package com.myshop.order.ui;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.member.domain.MemberId;
import com.myshop.member.query.dto.MemberAuthentication;
import com.myshop.order.application.PlaceOrderService;
import com.myshop.order.domain.model.OrderNo;
import com.myshop.order.domain.model.Orderer;
import com.myshop.order.error.PlaceOrderErrorResponse;
import com.myshop.order.error.ValidationErrorException;
import com.myshop.order.query.dto.OrderRequest;

@RestController
public class OrderController {

	private final PlaceOrderService placeOrderService;

	public OrderController(PlaceOrderService placeOrderService) {
		this.placeOrderService = placeOrderService;
	}

	@PostMapping("/order/place")
	public ResponseEntity<?> order(@RequestBody OrderRequest orderRequest, Errors errors) {
		setOrderer(orderRequest);
		OrderNo orderNo;
		try {
			orderNo = placeOrderService.placeOrder(orderRequest);
		} catch (ValidationErrorException e) {
			e.getErrors().forEach(err -> {
				if (err.hasName()) {
					errors.rejectValue(err.getPropertyName(), err.getCode());
				} else {
					errors.reject(err.getCode());
				}
			});
			List<ErrorResponse> errorResponses = createErrorResponses(errors);
			return ResponseEntity.badRequest().body(errorResponses);
		}

		return ResponseEntity.ok(orderNo);
	}

	private List<ErrorResponse> createErrorResponses(Errors errors) {
		return errors.getFieldErrors().stream()
			.map(err -> new PlaceOrderErrorResponse(err.getField(), err.getDefaultMessage(), err.getCode()))
			.map(ErrorResponse.class::cast)
			.toList();
	}

	private void setOrderer(OrderRequest orderRequest) {
		Orderer orderer = createOrderer();
		orderRequest.setOrdererMemberId(orderer.getMemberId());
	}

	private static Orderer createOrderer() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		MemberAuthentication memberAuthentication = (MemberAuthentication)authentication.getPrincipal();
		MemberId memberId = new MemberId(memberAuthentication.getMemberId());
		String name = memberAuthentication.getMemberName();
		return new Orderer(memberId, name);
	}
}
