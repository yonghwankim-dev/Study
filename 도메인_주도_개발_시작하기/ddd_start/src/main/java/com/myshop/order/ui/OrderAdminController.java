package com.myshop.order.ui;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.order.command.application.StartShippingService;
import com.myshop.order.error.VersionConflictException;
import com.myshop.order.query.dto.StartShippingRequest;

@RestController
public class OrderAdminController {

	private final StartShippingService startShippingService;

	public OrderAdminController(StartShippingService startShippingService) {
		this.startShippingService = startShippingService;
	}

	@PostMapping("/startShipping")
	public ResponseEntity<Void> startShipping(@RequestBody StartShippingRequest request) {
		try {
			startShippingService.startShipping(request);
		} catch (OptimisticLockingFailureException | VersionConflictException exception) {
			return ResponseEntity.status(409).build(); // 409 Conflict
		}
		return ResponseEntity.ok().build();
	}
}
