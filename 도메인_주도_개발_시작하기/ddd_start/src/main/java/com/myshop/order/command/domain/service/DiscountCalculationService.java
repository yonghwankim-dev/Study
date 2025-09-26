package com.myshop.order.command.domain.service;

import java.util.List;

import com.myshop.common.model.Money;
import com.myshop.member.domain.MemberId;
import com.myshop.order.command.domain.model.OrderLine;

public interface DiscountCalculationService {
	Money calculateDiscountAmount(List<OrderLine> orderLines, MemberId ordererId);
}
