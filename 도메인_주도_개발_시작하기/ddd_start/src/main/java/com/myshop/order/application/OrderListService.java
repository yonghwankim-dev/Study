package com.myshop.order.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.domain.repository.OrderViewDao;
import com.myshop.order.query.dto.OrderView;

@Service
public class OrderListService {

	private final OrderViewDao orderViewDao;

	public OrderListService(OrderViewDao orderViewDao) {
		this.orderViewDao = orderViewDao;
	}

	@Transactional(readOnly = true)
	public List<OrderView> getOrderList(String ordererId) {
		return orderViewDao.selectByOrderer(ordererId);
	}
}
