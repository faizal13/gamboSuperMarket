package com.gamboSupermarket.application.model;

import java.util.List;
import java.util.Map;

import com.gamboSupermarket.application.dto.Product;

import lombok.Data;

@Data
public class AllProductResponse {

	private Map<String, List<Product>> categoryProducts;
	private List<Product> featuredProducts;

}
