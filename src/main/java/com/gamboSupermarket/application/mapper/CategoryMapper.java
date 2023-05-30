package com.gamboSupermarket.application.mapper;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gamboSupermarket.application.dto.Categories;
import com.gamboSupermarket.application.model.AddCategoryRequest;
import com.gamboSupermarket.application.services.UserServiceImpl;
import com.gamboSupermarket.application.utils.Utility;

@Component
public class CategoryMapper {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryMapper.class);

	public Categories mapAddCategoryToDto(AddCategoryRequest addCategoryRequest, Categories categories) throws IOException {

		if (addCategoryRequest != null) {
			categories.setName(addCategoryRequest.getName());
			categories.setStatus(addCategoryRequest.isStatus());
			categories.setDesc(addCategoryRequest.getDesc());
			categories.setPicByte(addCategoryRequest.getImage().getBytes());
					//Utility.compressBytes(addCategoryRequest.getImage().getBytes()));
		}
		return categories;
	}
}
