package jpabook.jpashop.controller.order;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.controller.order.request.OrderCreateRequest;
import jpabook.jpashop.controller.order.response.OrderCreateResponse;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.service.persistence.order.PersistenceOrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderRestController {
	private final PersistenceOrderService persistenceOrderService;

	@PostMapping
	public OrderCreateResponse order(@RequestBody OrderCreateRequest request) {
		Long orderId = persistenceOrderService.order(request.getMemberId(), request.getItemId(), request.getCount());
		Order order = persistenceOrderService.fineOne(orderId);

		return OrderCreateResponse.from(order);
	}
}
