package com.gamboSupermarket.application.model;

import lombok.Data;

@Data
public class BookedProductDetail {
	
	private SingleObjectRequest productInfo;
	
	private int shortFallQuantity;

}
