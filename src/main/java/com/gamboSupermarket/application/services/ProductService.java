package com.gamboSupermarket.application.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gamboSupermarket.application.dto.Categories;
import com.gamboSupermarket.application.dto.Product;
import com.gamboSupermarket.application.dto.ProductImages;
import com.gamboSupermarket.application.mapper.ProductMapper;
import com.gamboSupermarket.application.model.AddProductRequest;
import com.gamboSupermarket.application.model.AllProductResponse;
import com.gamboSupermarket.application.model.ProductCategoryResponse;
import com.gamboSupermarket.application.model.ProductDetailResponse;
import com.gamboSupermarket.application.model.ProductRequest;
import com.gamboSupermarket.application.model.SingleObjectRequest;
import com.gamboSupermarket.application.repository.CategoriesRepository;
import com.gamboSupermarket.application.repository.ProductImageRepository;
import com.gamboSupermarket.application.repository.ProductRepository;

@Service
public class ProductService implements IProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Override
	public Product addProduct(AddProductRequest addProductRequest) throws IOException {
		ProductImages productImages = null;
		Product product = productRepository.findById(addProductRequest.getProductId()).orElse(null);
		if (product == null) {
			productImages = new ProductImages();
			product = new Product();
		} else {
			productImages = productImageRepository.findById(product.getId()).orElse(new ProductImages());
		}
		productRepository.save(productMapper.mapAddProductToDto(addProductRequest, productImages, product));
		if (product != null) {
			productImages.setId(product.getId());
			productImageRepository.save(productImages);
		}
		return product;
	}

	@Override
	public AllProductResponse getAllProducts() {
		Map<String, List<Product>> categoryProductsMap = new HashMap<>();
		List<SingleObjectRequest> categoryList = new ArrayList<>();
		List<Categories> allCategories = categoriesRepository.findAllByStatus(true);
		allCategories.stream().forEach(category -> {
			SingleObjectRequest categoryObject = new SingleObjectRequest();
			categoryObject.setId(category.getId());
			categoryObject.setValue(category.getName());
			categoryList.add(categoryObject);
		});

		List<Long> categoryIds = allCategories.stream().map(Categories::getId).collect(Collectors.toList());

		List<Product> products = productRepository.findByCategoryIdAndStatus(categoryIds, true);

		for (SingleObjectRequest category : categoryList) {
			List<Product> productList = new ArrayList<>();
			for (Product product : products) {
				if (product.getCategory().getId().equals(category.getId())) {
					productList.add(product);
				}
			}
			categoryProductsMap.put(category.getValue(), productList.stream().limit(10).collect(Collectors.toList()));
		}

		List<Product> featuredProductList = products.stream().filter(Product::isFeatured).limit(10)
				.collect(Collectors.toList());
		AllProductResponse allProductResponse = new AllProductResponse();

		allProductResponse.setCategoryProducts(categoryProductsMap);
		allProductResponse.setFeaturedProducts(featuredProductList);

		return allProductResponse;
	}

	@Override
	public ProductDetailResponse getProductDetail(Long id) {

		Product product = productRepository.findById(id).get();
		product.setProductImageByte(product.getProductImageByte());
		ProductImages productImages = productImageRepository.findById(product.getId()).orElse(null);
		ProductDetailResponse productDetailResponse = new ProductDetailResponse();
		if (productImages != null) {
			productDetailResponse.setImage1(productImages.getProductImageByte1());
			productDetailResponse.setImage2(productImages.getProductImageByte2());
			productDetailResponse.setImage3(productImages.getProductImageByte3());
		}
		productDetailResponse.setProductDetail(product);
		// Utility.decompressBytes(product.getProductImageByte()));
		return productDetailResponse;
	}

	@Override
	public Product getProduct(Long id) {
		Product product = productRepository.findById(id).get();
		product.setProductImageByte(product.getProductImageByte());
		// Utility.decompressBytes(product.getProductImageByte()));
		return product;
	}

	@Override
	public ProductCategoryResponse getProductsByCategory(ProductRequest productRequest) {
		Pageable paging = PageRequest.of(productRequest.getPageNumber(), productRequest.getPageSize());
		Page<Product> products = productRepository.getProductsByCategory(productRequest.getCategoryId(), true, paging);
		ProductCategoryResponse productCategoryResponse = new ProductCategoryResponse();
		productCategoryResponse.setTotalPages(products.getTotalPages());
		if (products.hasContent()) {
			productCategoryResponse.setProducts(products.getContent());
		}

		return productCategoryResponse;
	}

}
