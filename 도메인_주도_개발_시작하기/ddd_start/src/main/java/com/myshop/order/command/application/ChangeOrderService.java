package com.myshop.order.command.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;
import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.command.domain.model.ShippingInfo;
import com.myshop.order.command.domain.repository.OrderRepository;
import com.myshop.order.error.OrderNotFoundException;

@Service
public class ChangeOrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;

	public ChangeOrderService(OrderRepository orderRepository, MemberRepository memberRepository) {
		this.orderRepository = orderRepository;
		this.memberRepository = memberRepository;
	}

	@Transactional
	public void changeShippingInfo(OrderNo id, ShippingInfo newShippingInfo, boolean useNewShippingAddrAsMemberAddr) {
		Order order = orderRepository.findByIdForUpdate(id)
			.orElseThrow(() -> new OrderNotFoundException(id));
		order.changeShippingInfo(newShippingInfo);
		if (useNewShippingAddrAsMemberAddr) {
			Member member = memberRepository.findById(order.getOrderer().getMemberId());
			member.changeAddress(newShippingInfo.getAddress());
		}
	}
}
