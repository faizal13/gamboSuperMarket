package com.gamboSupermarket.application.model;

import com.gamboSupermarket.application.dto.Product;

import lombok.Data;

@Data
public class ProductDetailResponse {

	private Product productDetail;
	private byte[] image1;
	private byte[] image2;
	private byte[] image3;

}
