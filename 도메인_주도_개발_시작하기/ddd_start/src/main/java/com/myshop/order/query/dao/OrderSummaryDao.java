package com.myshop.order.query.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import com.myshop.order.query.dto.OrderSummary;

public interface OrderSummaryDao extends Repository<OrderSummary, String> {
	List<OrderSummary> findAll(Specification<OrderSummary> spec);
}
