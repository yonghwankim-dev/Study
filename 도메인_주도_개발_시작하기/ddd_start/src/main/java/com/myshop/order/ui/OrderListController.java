package com.myshop.order.ui;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.member.domain.MemberId;
import com.myshop.member.query.dto.MemberAuthentication;
import com.myshop.order.application.OrderListService;
import com.myshop.order.domain.Orderer;
import com.myshop.order.query.dto.OrderView;

@RestController
public class OrderListController {

	private final OrderListService orderListService;

	public OrderListController(OrderListService orderListService) {
		this.orderListService = orderListService;
	}

	@GetMapping("/myorders")
	public ResponseEntity<List<OrderView>> list() {
		MemberId memberId = createOrderer().getMemberId();
		List<OrderView> orderViews = orderListService.getOrderList(memberId.getId());
		return ResponseEntity.ok().body(orderViews);
	}

	private Orderer createOrderer() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberAuthentication memberAuthentication = (MemberAuthentication)authentication.getPrincipal();
		MemberId memberId = new MemberId(memberAuthentication.getMemberId());
		String name = memberAuthentication.getMemberName();
		return new Orderer(memberId, name);
	}
}
