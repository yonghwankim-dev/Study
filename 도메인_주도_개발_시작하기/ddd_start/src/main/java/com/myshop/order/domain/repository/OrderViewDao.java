package com.myshop.order.domain.repository;

import java.util.List;

import com.myshop.order.query.dto.OrderView;

public interface OrderViewDao {

	List<OrderView> selectByOrderer(String ordererId);
}
