package com.gamboSupermarket.application.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gamboSupermarket.application.dto.Categories;
import com.gamboSupermarket.application.model.AddCategoryRequest;
import com.gamboSupermarket.application.model.CustomResponse;
import com.gamboSupermarket.application.model.EmptyJsonResponse;
import com.gamboSupermarket.application.services.ICategoryService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping(path = "/api")
public class CategoriesController {

	@Autowired
	ICategoryService categoryService;

	@PostMapping(value = "/categories/addCategory")
	public ResponseEntity<CustomResponse<Object>> addCategory(@ModelAttribute AddCategoryRequest addCategoryRequest)
			throws IOException {
		categoryService.addCategory(addCategoryRequest);
		CustomResponse<Object> response = new CustomResponse<>(200, "category added successfully",
				new EmptyJsonResponse());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(path = { "/auth/categories/getCategories" })
	public ResponseEntity<CustomResponse<List<Categories>>> getAllCategories() throws IOException {
		List<Categories> categories = categoryService.getAllCategories();
		CustomResponse<List<Categories>> response = new CustomResponse<>(200, "Success", categories);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
