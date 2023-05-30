package com.gamboSupermarket.application.mapper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.gamboSupermarket.application.dto.Product;
import com.gamboSupermarket.application.dto.ProductImages;
import com.gamboSupermarket.application.model.AddProductRequest;
import com.gamboSupermarket.application.repository.CategoriesRepository;
import com.gamboSupermarket.application.utils.CartUtility;
import com.gamboSupermarket.application.utils.Utility;

@Component
public class ProductMapper {

	private static final Logger logger = LoggerFactory.getLogger(ProductMapper.class);

	@Autowired
	CategoriesRepository categoriesRepository;

	public Product mapAddProductToDto(AddProductRequest addProductRequest, ProductImages productImages, Product product)
			throws IOException {

		if (addProductRequest != null) {
			product.setCategory(categoriesRepository.findById(addProductRequest.getCategoryId()).get());
			product.setDescription(addProductRequest.getDescription());
			product.setDiscountPrice(addProductRequest.getDiscountPrice());
			product.setPrice(addProductRequest.getPrice());
			product.setProductName(addProductRequest.getName());
			product.setQuantity(addProductRequest.getQuantity());
			product.setStatus(addProductRequest.isStatus());
			List<MultipartFile> imageList = Arrays.asList(addProductRequest.getProductImage());
			if (!Utility.isEmpty(imageList)) {
				product.setProductImageByte(imageList.get(0).getBytes());
				if (imageList.size() >= 2) {
					productImages.setProductImageByte1(imageList.get(1).getBytes());
				}
				if (imageList.size() >= 3) {
					productImages.setProductImageByte2(imageList.get(2).getBytes());
				}
				if (imageList.size() >= 4) {
					productImages.setProductImageByte3(imageList.get(3).getBytes());
				}
			}

			// Utility.compressBytes(addProductRequest.getProductImage().getBytes()));
			product.setModified_on(new Timestamp(System.currentTimeMillis()));
			product.setFeatured(addProductRequest.isFeatured());
			product.setPercentDiscount(
					CartUtility.getPercentDiscount(addProductRequest.getPrice(), addProductRequest.getDiscountPrice()));
		}
		return product;
	}
}
