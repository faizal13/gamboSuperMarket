package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class AddToCartRequest {
	
	private String refNumber;

	private SingleObjectRequest productData;

	private int quantity;

}
