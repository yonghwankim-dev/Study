package com.myshop.order;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.common.model.Money;
import com.myshop.member.domain.MemberId;
import com.myshop.order.application.CancelOrderService;
import com.myshop.order.domain.Address;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderLine;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;
import com.myshop.order.domain.OrderState;
import com.myshop.order.domain.Orderer;
import com.myshop.order.domain.Receiver;
import com.myshop.order.domain.ShippingInfo;

class CancelOrderServiceTest {

	private String id;
	private OrderNo orderNo;
	private Orderer orderer;
	private List<OrderLine> orderLines;
	private ShippingInfo shippingInfo;

	private Order createOrder() {
		OrderState state = OrderState.PAYMENT_WAITING;
		return new Order(orderNo, orderer, orderLines, shippingInfo, state);
	}

	@BeforeEach
	void setUp() {
		id = "12345";
		orderNo = new OrderNo(id);
		MemberId memberId = new MemberId("12345");
		orderer = new Orderer(memberId, "John Doe");
		ProductId productId = new ProductId("9000000112298");
		OrderLine orderLine = new OrderLine(productId, new Money(1000), 2);
		orderLines = List.of(orderLine);
		shippingInfo = new ShippingInfo(
			new Receiver("John Doe", "1234567890"),
			new Address("123 Main St", "City", "12345")
		);
	}

	@Test
	void shouldDoesNotThrow_whenOrderIsNotShipped() {
		OrderRepository repository = BDDMockito.mock(OrderRepository.class);
		OrderNo number = new OrderNo(id);
		Order order = createOrder();
		BDDMockito.given(repository.findById(number))
			.willReturn(order);
		CancelOrderService service = new CancelOrderService(repository);

		Assertions.assertDoesNotThrow(() -> service.cancelOrder(number));
	}
}
