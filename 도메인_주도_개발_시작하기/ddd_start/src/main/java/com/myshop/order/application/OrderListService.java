package com.myshop.order.application;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.query.dto.OrderView;

@Service
public class OrderListService {

	@Transactional(readOnly = true)
	public List<OrderView> getOrderList(String ordererId) {
		return Collections.emptyList();
	}
}
