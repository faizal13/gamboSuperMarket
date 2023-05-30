package com.gamboSupermarket.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gamboSupermarket.application.dto.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Order findByReferenceIdAndOrderStatus(String refId, String code);
	
	Order findByReferenceId(String refId);

	@Query(value = "SELECT o.reference_number FROM orders o WHERE o.user_id = ?1 AND o.order_status = ?2", nativeQuery = true)
	String getRefNumber(long id, String code);

	long countByOrderStatus(String code);

	@Query(value = "SELECT SUM(o.total_amount) FROM orders o WHERE o.order_status = ?1", nativeQuery = true)
	Optional<Double> getTotalIncome(String code);

	@Modifying
	@Query("UPDATE Order o SET o.orderStatus = ?1 WHERE o.referenceId = ?2")
	int updateStatus(String title, String id);
	
	@Modifying
	@Query("DELETE FROM Order o WHERE o.userDetail.id = ?1 AND o.orderStatus = ?2")
	void deleteCart(long id, String code);
	
	@Query(value = "SELECT * FROM orders o WHERE o.user_id = ?1 AND o.order_status = ?2", nativeQuery = true)
	Order getOrderByUserIdAndStatus(long id, String code);
	
	@Query(value = "SELECT * FROM orders o WHERE o.order_status != ?1", nativeQuery = true)
	Page<Order> getAllOrdersByStatus(String code, Pageable pageable);
	
	@Query(value = "SELECT * FROM orders o WHERE o.user_id = ?1 ORDER BY o.order_id DESC", nativeQuery = true)
	List<Order> getOrderByUserId(long id);

}
