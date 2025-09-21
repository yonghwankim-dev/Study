package com.myshop.order.infrastructure;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myshop.order.domain.model.Order;
import com.myshop.order.domain.model.OrderNo;
import com.myshop.order.query.dto.OrderSummary;

public interface SpringDataJpaOrderRepository extends JpaRepository<Order, OrderNo> {

	@Query(value = "SELECT o.* FROM purchase_order o "
		+ "WHERE o.orderer_id = :ordererId "
		+ "ORDER BY o.order_no DESC "
		+ "LIMIT :size OFFSET :startRow",
		nativeQuery = true
	)
	List<Order> findByOrdererId(@Param("ordererId") String ordererId, @Param("startRow") int startRow,
		@Param("size") int size);

	List<Order> findAll(Specification<OrderSummary> spec);

}
