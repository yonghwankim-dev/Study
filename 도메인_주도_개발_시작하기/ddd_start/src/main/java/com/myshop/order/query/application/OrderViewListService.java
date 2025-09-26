package com.myshop.order.query.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.command.domain.repository.OrderViewDao;
import com.myshop.order.query.dto.OrderView;

@Service
public class OrderViewListService {

	private final OrderViewDao orderViewDao;

	public OrderViewListService(OrderViewDao orderViewDao) {
		this.orderViewDao = orderViewDao;
	}

	@Transactional(readOnly = true)
	public List<OrderView> getOrderList(String ordererId) {
		return orderViewDao.selectByOrderer(ordererId);
	}
}
