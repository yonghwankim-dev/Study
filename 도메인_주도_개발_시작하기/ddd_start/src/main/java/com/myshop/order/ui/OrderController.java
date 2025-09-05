package com.myshop.order.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.member.domain.MemberId;
import com.myshop.member.query.dto.MemberAuthentication;
import com.myshop.order.application.PlaceOrderService;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.Orderer;
import com.myshop.order.query.dto.OrderRequest;

@RestController
public class OrderController {

	private final PlaceOrderService placeOrderService;

	public OrderController(PlaceOrderService placeOrderService) {
		this.placeOrderService = placeOrderService;
	}

	@PostMapping("/order/place")
	public ResponseEntity<OrderNo> order(OrderRequest orderRequest) {
		setOrderer(orderRequest);
		OrderNo orderNo = placeOrderService.placeOrder(orderRequest);
		return ResponseEntity.ok(orderNo);
	}

	private void setOrderer(OrderRequest orderRequest) {
		Orderer orderer = createOrderer();
		orderRequest.setOrderer(orderer);
	}

	private static Orderer createOrderer() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberAuthentication memberAuthentication = (MemberAuthentication)authentication.getPrincipal();
		MemberId memberId = new MemberId(memberAuthentication.getMemberId());
		String name = memberAuthentication.getMemberName();
		return new Orderer(memberId, name);
	}
}
