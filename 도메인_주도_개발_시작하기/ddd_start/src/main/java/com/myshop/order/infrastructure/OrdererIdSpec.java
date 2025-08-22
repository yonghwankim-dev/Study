package com.myshop.order.infrastructure;

import org.springframework.data.jpa.domain.Specification;

import com.myshop.order.query.dto.OrderSummary;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class OrdererIdSpec implements Specification<OrderSummary> {

	@Override
	public Predicate toPredicate(Root<OrderSummary> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return null;
	}
}
