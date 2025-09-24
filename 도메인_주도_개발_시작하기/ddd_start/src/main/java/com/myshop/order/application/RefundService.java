package com.myshop.order.application;

import org.springframework.stereotype.Service;

@Service
public class RefundService {
	public void refund(String paymentId) {
		System.out.println("Refund processed for payment ID: " + paymentId);
	}
}
