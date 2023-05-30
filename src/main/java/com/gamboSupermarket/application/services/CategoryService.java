package com.gamboSupermarket.application.services;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamboSupermarket.application.dto.Categories;
import com.gamboSupermarket.application.mapper.CategoryMapper;
import com.gamboSupermarket.application.model.AddCategoryRequest;
import com.gamboSupermarket.application.repository.CategoriesRepository;

@Service
public class CategoryService implements ICategoryService {

	private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	CategoryMapper categoryMapper;

	@Autowired
	CategoriesRepository categoriesRepository;

	@Override
	public Categories addCategory(AddCategoryRequest addCategoryRequest) throws IOException {
		Categories category = categoriesRepository.findById(addCategoryRequest.getCategoryId())
				.orElse(new Categories());
		return categoriesRepository.save(categoryMapper.mapAddCategoryToDto(addCategoryRequest, category));
	}

	@Override
	public List<Categories> getAllCategories() {
		List<Categories> categories = categoriesRepository.findAllByStatus(true);
		// categories.stream().forEach(category ->
		// category.setPicByte(Utility.decompressBytes(category.getPicByte())));
		return categories;
	}

}
