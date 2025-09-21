package com.myshop.order.application;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.common.model.Money;
import com.myshop.member.domain.MemberId;
import com.myshop.order.domain.Address;
import com.myshop.order.domain.CancelPolicy;
import com.myshop.order.domain.Canceller;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderLine;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderState;
import com.myshop.order.domain.Orderer;
import com.myshop.order.domain.Receiver;
import com.myshop.order.domain.ShippingInfo;
import com.myshop.order.domain.repository.OrderRepository;

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
			"shipping message",
			new Address("123 Main St", "City", "12345"));
	}

	@Test
	void shouldDoesNotThrow_whenOrderIsNotShipped() {
		OrderRepository repository = BDDMockito.mock(OrderRepository.class);
		Optional<Order> order = Optional.of(createOrder());
		BDDMockito.given(repository.findById(orderNo))
			.willReturn(order);
		CancelPolicy cancelPolicy = BDDMockito.mock(CancelPolicy.class);
		Canceller canceller = new Canceller("12345");
		BDDMockito.given(cancelPolicy.hasCancellationPermission(order.orElseThrow(), canceller))
			.willReturn(true);
		CancelOrderService service = new CancelOrderService(repository, cancelPolicy);

		Assertions.assertDoesNotThrow(() -> service.cancelOrder(orderNo, canceller));
	}
}
