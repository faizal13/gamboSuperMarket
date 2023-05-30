package com.gamboSupermarket.application.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gamboSupermarket.application.dto.Product;
import com.gamboSupermarket.application.dto.ProductImages;

public interface ProductImageRepository extends JpaRepository<ProductImages, Long> {

	@Query(value = "SELECT * FROM products", nativeQuery = true)
	List<Product> getAllProducts();

	@Query(value = "SELECT * FROM products p WHERE p.category_id = ?1", nativeQuery = true)
	Page<Product> getProductsByCategory(long id, Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.category_id IN (:categoryIds)")
	List<Product> findByCategoryId(@Param("categoryIds") List<Long> categoryIds);

}
