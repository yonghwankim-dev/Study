package com.myshop.order.ui;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.myshop.member.query.dto.MemberAuthentication;
import com.myshop.order.application.OrderListService;
import com.myshop.order.query.dto.OrderView;

@Controller
public class ListOrderFormController {

	private final OrderListService orderListService;

	public ListOrderFormController(OrderListService orderListService) {
		this.orderListService = orderListService;
	}

	@GetMapping("/orders")
	public String listOrderForm(ModelMap model) {
		String ordererId = getAuthenticatedMemberId();
		List<OrderView> orderViews = orderListService.getOrderList(ordererId);
		model.addAttribute("orders", orderViews);
		return "order/list";
	}

	private String getAuthenticatedMemberId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberAuthentication memberAuth = (MemberAuthentication)authentication.getPrincipal();
		return memberAuth.getMemberId();
	}
}
