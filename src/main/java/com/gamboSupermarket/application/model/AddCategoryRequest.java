package com.gamboSupermarket.application.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddCategoryRequest {

	private long categoryId;

	private String name;

	private boolean status;

	private String desc;

	private MultipartFile image;
}
