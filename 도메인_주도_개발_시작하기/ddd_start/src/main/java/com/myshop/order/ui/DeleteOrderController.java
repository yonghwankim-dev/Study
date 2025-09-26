package com.myshop.order.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myshop.order.command.application.DeleteOrderService;

@Controller
public class DeleteOrderController {

	private final DeleteOrderService service;

	public DeleteOrderController(DeleteOrderService service) {
		this.service = service;
	}

	@PostMapping("/orders/delete")
	public String delete(@RequestParam("id") String orderId) {
		service.deleteOrder(orderId);
		return "order/list";
	}
}
