package com.gamboSupermarket.application.services;

import java.io.IOException;
import java.util.List;

import com.gamboSupermarket.application.dto.Categories;
import com.gamboSupermarket.application.model.AddCategoryRequest;

public interface ICategoryService {
	
	public Categories addCategory(AddCategoryRequest addCategoryRequest) throws IOException;

	public List<Categories> getAllCategories(); 
}
