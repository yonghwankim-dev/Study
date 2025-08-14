package com.myshop.order.application;

import org.springframework.transaction.annotation.Transactional;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;
import com.myshop.order.NoOrderException;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;
import com.myshop.order.domain.ShippingInfo;

public class ChangeOrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;

	public ChangeOrderService(OrderRepository orderRepository, MemberRepository memberRepository) {
		this.orderRepository = orderRepository;
		this.memberRepository = memberRepository;
	}

	@Transactional
	public void changeShippingInfo(OrderNo id, ShippingInfo newShippingInfo, boolean useNewShippingAddrAsMemberAddr){
		Order order = orderRepository.findById(id);
		if (order == null){
			throw new NoOrderException(id);
		}
		order.changeShippingInfo(newShippingInfo);
		if (useNewShippingAddrAsMemberAddr){
			Member member = memberRepository.findById(order.getOrderer().getMemberId());
			member.changeAddress(newShippingInfo.getAddress());
		}
	}
}
