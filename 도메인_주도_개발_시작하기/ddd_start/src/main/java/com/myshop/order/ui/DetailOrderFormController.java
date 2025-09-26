package com.myshop.order.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.query.dao.OrderViewDao;
import com.myshop.order.query.dto.OrderView;

@Controller
public class DetailOrderFormController {

	private final OrderViewDao orderViewDao;

	public DetailOrderFormController(OrderViewDao orderViewDao) {
		this.orderViewDao = orderViewDao;
	}

	@GetMapping("/orders/detail")
	public String detail(@RequestParam("id") String orderId, ModelMap model) {
		OrderView orderView = orderViewDao.findByOrderNo(new OrderNo(orderId));
		model.addAttribute("order", orderView);
		return "order/detail";
	}
}
