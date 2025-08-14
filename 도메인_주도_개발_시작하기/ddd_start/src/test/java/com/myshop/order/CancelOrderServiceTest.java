package com.myshop.order;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

class CancelOrderServiceTest {

	private String id;

	private Order createOrder() {
		OrderNo orderNo = new OrderNo(id);
		Orderer orderer = new Orderer();
		OrderLine orderLine = new OrderLine(new Product(), new Money(1000), 2);
		List<OrderLine> orderLines = List.of(orderLine);
		ShippingInfo shippingInfo = new ShippingInfo(
			new Receiver("John Doe", "1234567890"),
			new Address("123 Main St", "City", "12345")
		);
		OrderState state = OrderState.PAYMENT_WAITING;
		return new Order(orderNo, orderer, orderLines, shippingInfo, state);
	}

	@BeforeEach
	void setUp() {
		id = "12345";
	}

	@Test
	void shouldCancelOrder(){
		OrderRepository repository = BDDMockito.mock(OrderRepository.class);
		String orderId = id;
		Order order = createOrder();
		BDDMockito.given(repository.findById(orderId))
			.willReturn(order);
		CancelOrderService service = new CancelOrderService(repository);

		service.cancelOrder(orderId);
	}
}
