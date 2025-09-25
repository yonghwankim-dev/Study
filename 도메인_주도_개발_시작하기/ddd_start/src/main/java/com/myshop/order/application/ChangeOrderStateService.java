package com.myshop.order.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.order.domain.model.Order;
import com.myshop.order.domain.model.OrderState;

@Service
public class ChangeOrderStateService {

	@Transactional
	public void changeOrderState(Order order, OrderState state) {
		order.changeState(state);
	}
}
