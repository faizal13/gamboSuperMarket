package com.gamboSupermarket.application.services;

import java.io.IOException;

import com.gamboSupermarket.application.dto.Product;
import com.gamboSupermarket.application.model.AddProductRequest;
import com.gamboSupermarket.application.model.AllProductResponse;
import com.gamboSupermarket.application.model.ProductCategoryResponse;
import com.gamboSupermarket.application.model.ProductDetailResponse;
import com.gamboSupermarket.application.model.ProductRequest;

public interface IProductService {

	public Product addProduct(AddProductRequest addProductRequest) throws IOException;

	public AllProductResponse getAllProducts();
	
	public Product getProduct(Long id);

	public ProductDetailResponse getProductDetail(Long id);
	
	public ProductCategoryResponse getProductsByCategory(ProductRequest productRequest);

}
