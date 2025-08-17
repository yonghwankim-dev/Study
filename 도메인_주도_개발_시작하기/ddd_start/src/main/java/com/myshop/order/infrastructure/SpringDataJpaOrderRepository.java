package com.myshop.order.infrastructure;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;

public interface SpringDataJpaOrderRepository extends JpaRepository<Order, OrderNo> {

	@Query("SELECT o FROM Order o "
		+ "WHERE o.orderer.memberId.id = :ordererId "
		+ "ORDER BY o.orderNo.id DESC")
	List<Order> findByOrdererId(@Param("ordererId") String ordererId, Pageable pageable);

}
