package com.myshop.order.domain.service;

import java.util.List;

import com.myshop.common.model.Money;
import com.myshop.order.domain.model.OrderLine;

public interface DiscountCalculationService {
	Money calculateDiscountAmount(List<OrderLine> orderLines);
}
