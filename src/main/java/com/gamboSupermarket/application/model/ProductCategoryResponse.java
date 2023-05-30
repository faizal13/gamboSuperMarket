package com.gamboSupermarket.application.model;

import java.util.List;

import com.gamboSupermarket.application.dto.Product;

import lombok.Data;

@Data
public class ProductCategoryResponse {

	private List<Product> products;
	private int totalPages;

}
