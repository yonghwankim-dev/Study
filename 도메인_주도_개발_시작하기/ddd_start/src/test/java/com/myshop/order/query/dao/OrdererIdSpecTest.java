package com.myshop.order.query.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import com.myshop.order.query.dto.OrderSummary;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

class OrdererIdSpecTest {

	@Test
	void canCreated() {
		String ordererId = "12345";
		Specification<OrderSummary> spec = new OrdererIdSpec(ordererId);

		Assertions.assertThat(spec).isNotNull();
	}

	@Test
	void shouldReturnPredicate() {
		String ordererId = "12345";
		Specification<OrderSummary> spec = new OrdererIdSpec(ordererId);

		Root<OrderSummary> root = null;
		CriteriaQuery<?> query = null;
		CriteriaBuilder criteriaBuilder = null;
		Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);

		Assertions.assertThat(predicate).isNotNull();
	}
}
