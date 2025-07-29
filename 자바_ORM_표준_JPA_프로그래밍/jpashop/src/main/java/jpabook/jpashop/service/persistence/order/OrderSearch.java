package jpabook.jpashop.service.persistence.order;

import jpabook.jpashop.domain.order.OrderStatus;
import lombok.Getter;

@Getter
public class OrderSearch {

	private String memberName;    // 회원이름
	private OrderStatus orderStatus;    // 주문상태 [ORDER, CANCEL]

	public OrderSearch(String memberName, OrderStatus orderStatus) {
		this.memberName = memberName;
		this.orderStatus = orderStatus;
	}
}
