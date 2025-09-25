package com.myshop.order.ui;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.member.query.dto.MemberAuthentication;
import com.myshop.order.application.CancelOrderService;
import com.myshop.order.domain.model.Canceller;
import com.myshop.order.domain.model.OrderNo;

@RestController
public class CancelOrderController {

	private final CancelOrderService service;

	public CancelOrderController(CancelOrderService service) {
		this.service = service;
	}

	@PostMapping("/orders/cancel")
	public String cancelOrder(@RequestParam String orderId) {
		String memberId = getAuthenticateMemberId();
		Canceller canceller = new Canceller(memberId);
		service.cancelOrder(new OrderNo(orderId), canceller);
		return "Order canceled";
	}

	private String getAuthenticateMemberId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberAuthentication memberAuth = (MemberAuthentication)authentication.getPrincipal();
		return memberAuth.getMemberId();
	}
}
