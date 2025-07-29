package jpabook.jpashop.controller.order.request;

import lombok.Getter;

@Getter
public class OrderCreateRequest {
	private Long memberId;
	private Long itemId;
	private Integer count;
}
