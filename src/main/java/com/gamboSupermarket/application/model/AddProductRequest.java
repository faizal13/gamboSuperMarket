package com.gamboSupermarket.application.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddProductRequest {

	private long productId;

	private String name;

	private long categoryId;

	private double price;

	private double discountPrice;

	private int quantity;

	private boolean status;

	private String description;

	private MultipartFile[] productImage;

	private boolean featured;

}
