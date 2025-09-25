package com.myshop.order.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myshop.order.application.ChangeOrderStateService;
import com.myshop.order.domain.model.OrderNo;
import com.myshop.order.domain.model.OrderState;

@Controller
public class ChangeOrderStateController {

	private final ChangeOrderStateService service;

	public ChangeOrderStateController(ChangeOrderStateService service) {
		this.service = service;
	}
	
	@PostMapping("/orders/change-state")
	public String changeOrderState(@RequestParam("id") String orderId, @RequestParam("state") String state) {
		OrderState orderState = OrderState.valueOf(state);
		service.changeOrderState(new OrderNo(orderId), orderState);
		return "redirect:/orders";
	}
}
