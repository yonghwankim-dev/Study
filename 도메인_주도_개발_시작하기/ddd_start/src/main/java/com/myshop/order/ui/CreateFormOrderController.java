package com.myshop.order.ui;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.myshop.member.application.SearchMemberService;
import com.myshop.member.domain.Member;
import com.myshop.member.query.dto.MemberAuthentication;

@Controller
public class CreateFormOrderController {

	private final SearchMemberService service;

	public CreateFormOrderController(SearchMemberService service) {
		this.service = service;
	}

	@GetMapping("/order/create")
	public String createForm(ModelMap modelMap) {
		Member orderer = service.search(getAuthenticatedMemberId());
		modelMap.addAttribute("ordererMemberId", orderer.getId());
		modelMap.addAttribute("receiverName", orderer.getName());
		modelMap.addAttribute("address1", orderer.getAddress().getAddress1());
		modelMap.addAttribute("address2", orderer.getAddress().getAddress2());
		modelMap.addAttribute("zipcode", orderer.getAddress().getZipCode());

		return "order/createForm";
	}

	private String getAuthenticatedMemberId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberAuthentication memberAuth = (MemberAuthentication)authentication.getPrincipal();
		return memberAuth.getMemberId();
	}
}
