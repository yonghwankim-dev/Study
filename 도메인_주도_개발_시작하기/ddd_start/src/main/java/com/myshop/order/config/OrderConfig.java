package com.myshop.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.myshop.order.domain.discount.DiscountCalculationService;

@Configuration
public class OrderConfig {
	@Bean
	public DiscountCalculationService discountCalculationService() {
		return new DiscountCalculationService();
	}
}
