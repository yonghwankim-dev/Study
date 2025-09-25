package com.myshop.order.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myshop.order.domain.model.OrderNo;
import com.myshop.order.domain.repository.OrderViewDao;
import com.myshop.order.query.dto.OrderView;

@Controller
public class ChangeOrderStateFormController {

	private final OrderViewDao orderViewDao;

	public ChangeOrderStateFormController(OrderViewDao orderViewDao) {
		this.orderViewDao = orderViewDao;
	}

	@GetMapping("/orders/change-state")
	public String changeOrderStateForm(@RequestParam("id") String orderId, ModelMap model) {
		OrderView orderView = orderViewDao.findByOrderNo(new OrderNo(orderId));
		model.addAttribute("order", orderView);
		return "order/orderStateEditForm";
	}
}
