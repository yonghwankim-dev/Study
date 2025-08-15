package com.myshop.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;

public interface SpringDataJpaOrderRepository extends JpaRepository<Order, OrderNo> {

}
