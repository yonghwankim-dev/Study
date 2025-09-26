package com.myshop.order.ui;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.member.domain.MemberId;
import com.myshop.member.query.dto.MemberAuthentication;
import com.myshop.order.query.dao.OrderViewDao;
import com.myshop.order.query.dto.OrderView;

@RestController
public class ListOrderController {

	private final OrderViewDao orderViewDao;

	public ListOrderController(OrderViewDao orderViewDao) {
		this.orderViewDao = orderViewDao;
	}

	@GetMapping("/myorders")
	public ResponseEntity<List<OrderView>> list() {
		MemberId memberId = getAuthenticatedMemberId();
		List<OrderView> orderViews = orderViewDao.selectByOrderer(memberId.getId());
		return ResponseEntity.ok().body(orderViews);
	}

	private MemberId getAuthenticatedMemberId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberAuthentication memberAuthentication = (MemberAuthentication)authentication.getPrincipal();
		return new MemberId(memberAuthentication.getMemberId());
	}
}
