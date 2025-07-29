package jpabook.jpashop.controller.order.response;

import jpabook.jpashop.domain.order.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCreateResponse {

	private Long id;

	public static OrderCreateResponse from(Order order) {
		return new OrderCreateResponse(order.getId());
	}
}
