package com.myshop.order.query.dao;

import org.springframework.data.jpa.domain.Specification;

import com.myshop.order.query.dto.OrderSummary;
import com.myshop.order.query.dto.OrderSummary_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class OrdererIdSpec implements Specification<OrderSummary> {

	private final String ordererId;

	public OrdererIdSpec(String ordererId) {
		this.ordererId = ordererId;
	}

	@Override
	public Predicate toPredicate(Root<OrderSummary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return cb.equal(root.get(OrderSummary_.ordererId), ordererId);
	}
}
