package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class ProductRequest {

	private Long categoryId;
	private int pageSize;
	private int pageNumber;

}
