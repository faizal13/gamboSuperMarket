package com.gamboSupermarket.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamboSupermarket.application.dto.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

	List<Categories> findAllByStatus(boolean status);
}
