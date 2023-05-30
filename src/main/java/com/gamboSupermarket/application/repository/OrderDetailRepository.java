package com.gamboSupermarket.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gamboSupermarket.application.dto.OrderDetail;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>  {
	
	@Modifying
	@Query("DELETE FROM OrderDetail od WHERE od.order.id = ?1")
	void deleteCartLine(long id);
	
}
