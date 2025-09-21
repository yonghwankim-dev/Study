package com.myshop.order.infrastructure;

import org.springframework.stereotype.Component;

import com.myshop.order.domain.model.OrderNoGenerator;

@Component
public class NanoTimeOrderNoGenerator implements OrderNoGenerator {
	@Override
	public String nextId() {
		return "ORDER-" + System.nanoTime();
	}
}
