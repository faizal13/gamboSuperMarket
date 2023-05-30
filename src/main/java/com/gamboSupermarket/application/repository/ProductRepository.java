package com.gamboSupermarket.application.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gamboSupermarket.application.dto.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "SELECT * FROM products", nativeQuery = true)
	List<Product> getAllProducts();

	@Query(value = "SELECT * FROM products p WHERE p.category_id = ?1 AND p.status = ?2", nativeQuery = true)
	Page<Product> getProductsByCategory(long id, boolean status, Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.category_id IN (:categoryIds) AND p.status = :status")
	List<Product> findByCategoryIdAndStatus(@Param("categoryIds") List<Long> categoryIds, @Param("status") boolean status);

}
