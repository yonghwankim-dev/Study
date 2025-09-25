package com.myshop.order.ui;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.myshop.catalog.application.product.ProductListService;
import com.myshop.catalog.query.product.dto.ProductDto;
import com.myshop.member.application.SearchMemberService;
import com.myshop.member.domain.Member;
import com.myshop.member.query.dto.MemberAuthentication;

@Controller
public class CreateFormOrderController {

	private final SearchMemberService service;
	private final ProductListService productListService;

	public CreateFormOrderController(SearchMemberService service, ProductListService productListService) {
		this.service = service;
		this.productListService = productListService;
	}

	@GetMapping("/order/create")
	public String createForm(ModelMap modelMap) {
		List<ProductDto> products = productListService.getAllProducts(1, 10).stream()
			.map(product -> new ProductDto(product.getId().getId(), product.getProductInfo().getProductName()))
			.toList();
		modelMap.addAttribute("products", products);

		Member orderer = service.search(getAuthenticatedMemberId());
		modelMap.addAttribute("ordererMemberId", orderer.getId().getId());
		modelMap.addAttribute("receiverName", orderer.getName());
		modelMap.addAttribute("address1", orderer.getAddress().getAddress1());
		modelMap.addAttribute("address2", orderer.getAddress().getAddress2());
		modelMap.addAttribute("zipCode", orderer.getAddress().getZipCode());

		return "order/createForm";
	}

	private String getAuthenticatedMemberId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberAuthentication memberAuth = (MemberAuthentication)authentication.getPrincipal();
		return memberAuth.getMemberId();
	}
}
