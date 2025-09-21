package com.myshop.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.myshop.order.infrastructure.BenefitPolicyDiscountService;

@Configuration
public class OrderConfig {
	@Bean
	public BenefitPolicyDiscountService discountCalculationService() {
		return new BenefitPolicyDiscountService();
	}
}
