package com.myshop.order;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

class CancelOrderServiceTest {

	private String id;
	private OrderNumber orderNumber;
	private Orderer orderer;
	private List<OrderLine> orderLines;
	private ShippingInfo shippingInfo;

	private Order createOrder() {
		OrderState state = OrderState.PAYMENT_WAITING;
		return new Order(orderNumber, orderer, orderLines, shippingInfo, state);
	}

	@BeforeEach
	void setUp() {
		id = "12345";
		orderNumber = new OrderNumber(id);
		orderer = new Orderer("John Doe", "johnDoe@gmail.com");
		OrderLine orderLine = new OrderLine(new Product(), new Money(1000), 2);
		orderLines = List.of(orderLine);
		shippingInfo = new ShippingInfo(
			new Receiver("John Doe", "1234567890"),
			new Address("123 Main St", "City", "12345")
		);
	}

	@Test
	void shouldDoesNotThrow_whenOrderIsNotShipped(){
		OrderRepository repository = BDDMockito.mock(OrderRepository.class);
		OrderNumber number = new OrderNumber(id);
		Order order = createOrder();
		BDDMockito.given(repository.findByNumber(number))
			.willReturn(order);
		CancelOrderService service = new CancelOrderService(repository);

		Assertions.assertDoesNotThrow(() -> service.cancelOrder(number));
	}
}
