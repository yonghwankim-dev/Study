package com.myshop.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.myshop.order.command.domain.service.CouponAndMemberShipDiscountCalculationService;

@Configuration
public class OrderConfig {
	@Bean
	public CouponAndMemberShipDiscountCalculationService discountCalculationService() {
		return new CouponAndMemberShipDiscountCalculationService();
	}
}
