package com.example.orderservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.orderservice.jpa.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
	OrderEntity findByOrderId(String orderId);
	Iterable<OrderEntity> findByUserId(String userId);
}
