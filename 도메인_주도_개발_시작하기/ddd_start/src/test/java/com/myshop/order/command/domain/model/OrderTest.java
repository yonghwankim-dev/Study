package com.myshop.order.command.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.common.model.Money;
import com.myshop.coupon.domain.Coupon;
import com.myshop.member.domain.MemberGrade;
import com.myshop.member.domain.MemberId;
import com.myshop.order.command.application.RefundService;
import com.myshop.order.command.domain.model.Address;
import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.model.OrderLine;
import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.command.domain.model.OrderState;
import com.myshop.order.command.domain.model.Orderer;
import com.myshop.order.command.domain.model.Receiver;
import com.myshop.order.command.domain.model.ShippingInfo;
import com.myshop.order.command.domain.service.CouponAndMemberShipDiscountCalculationService;
import com.myshop.order.command.domain.service.DiscountCalculationService;
import com.myshop.order.infrastructure.RuleBasedDiscountCalculationService;

class OrderTest {

	private ShippingInfo shippingInfo;
	private List<OrderLine> orderLines;
	private ShippingInfo newShippingInfo;
	private Orderer orderer;
	private OrderNo orderNo;

	public static Stream<Arguments> notShippedOrderStateSource() {
		return Stream.of(
			Arguments.of(OrderState.PAYMENT_WAITING),
			Arguments.of(OrderState.PREPARING)
		);
	}

	public static Stream<Arguments> alreadyShippedOrderStateSource() {
		return Stream.of(
			Arguments.of(OrderState.SHIPPED),
			Arguments.of(OrderState.DELIVERING),
			Arguments.of(OrderState.DELIVERY_COMPLETED),
			Arguments.of(OrderState.CANCELED)
		);
	}

	public static Stream<Arguments> invalidOrderLinesSource() {
		return Stream.of(
			Arguments.of(List.of()),
			Arguments.of((Object)null)
		);
	}

	@BeforeEach
	void setUp() {
		orderNo = new OrderNo("12345");
		MemberId memberId = new MemberId("12345");
		orderer = new Orderer(memberId, "John Doe");
		Receiver receiver = new Receiver("John Doe", "1234567890");
		Address address = new Address("123 Main St", "City", "12345");
		String message = "Please deliver between 9 AM and 5 PM";
		shippingInfo = new ShippingInfo(receiver, message, address);
		ProductId productId = new ProductId("9000000112298");
		orderLines = List.of(new OrderLine(productId, new Money(1000), 2));
		Receiver newReceiver = new Receiver("Jane Doe", "0987654321");
		String newMessage = "Please deliver to the back door.";
		Address newAddress = new Address("456 Elm St", "New City", "67890");
		newShippingInfo = new ShippingInfo(newReceiver, newMessage, newAddress);
	}

	@ParameterizedTest
	@MethodSource(value = "notShippedOrderStateSource")
	void shouldChangeShippingInfo_whenOrderStateIsPaymentWaiting(OrderState state) {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, state);

		order.changeShippingInfo(newShippingInfo);

		assertDoesNotThrow(() -> order.changeShippingInfo(newShippingInfo));
	}

	@ParameterizedTest
	@MethodSource(value = "alreadyShippedOrderStateSource")
	void shouldThrowException_whenOrderStateIsNotShippingChangeable(OrderState state) {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, state);

		Throwable throwable = Assertions.catchThrowable(() -> order.changeShippingInfo(newShippingInfo));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("already shipped");
	}

	@ParameterizedTest
	@MethodSource(value = "invalidOrderLinesSource")
	void shouldThrowException_whenOrderLinesIsInvalid(List<OrderLine> orderLines) {
		Throwable throwable = Assertions.catchThrowable(() -> new Order(orderNo, orderer, orderLines, shippingInfo,
			OrderState.PAYMENT_WAITING));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("no OrderLine");
	}

	@Test
	void shouldNotThrowException_whenShippingInfoIsValid() {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);

		assertDoesNotThrow(() -> order.changeShippingInfo(newShippingInfo));
	}

	@Test
	void shouldThrowException_whenShippingInfoIsNull() {
		Throwable throwable = Assertions.catchThrowable(
			() -> new Order(orderNo, orderer, orderLines, null, OrderState.PAYMENT_WAITING));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("no ShippingInfo");
	}

	@ParameterizedTest
	@MethodSource(value = "notShippedOrderStateSource")
	void shouldDoesNotThrow_whenOrderIsCanceled() {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);

		order.cancel();

		Assertions.assertThat(order.getState()).isEqualTo(OrderState.CANCELED);

	}

	@ParameterizedTest
	@MethodSource(value = "alreadyShippedOrderStateSource")
	void shouldThrow_whenOrderIsAlreadyShipped(OrderState state) {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, state);

		Throwable throwable = Assertions.catchThrowable(order::cancel);

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("already shipped");
	}

	@Test
	void shouldDoesNotThrow_whenOrdererIsNull() {
		Throwable throwable = Assertions.catchThrowable(
			() -> new Order(orderNo, null, orderLines, shippingInfo, OrderState.PAYMENT_WAITING));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("no Orderer");
	}

	@Test
	void shouldThrow_whenOrderNoIsNull() {
		Throwable throwable = Assertions.catchThrowable(
			() -> new Order(null, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("no OrderNo");
	}

	@Test
	void shouldReturnTrue_whenCompareOrder() {
		Order order1 = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);
		Order order2 = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.SHIPPED);

		boolean actual = order1.equals(order2);

		assertTrue(actual, "Orders should be equal based on OrderNo");
	}

	@Test
	void calculateAmounts() {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);
		CouponAndMemberShipDiscountCalculationService service = new CouponAndMemberShipDiscountCalculationService();
		MemberGrade grade = MemberGrade.vip();

		order.calculateAmounts(service, grade);

		Assertions.assertThat(order.getPaymentAmounts()).isEqualTo(new Money(1800));
	}

	@Test
	void calculateAmounts_whenAddedCoupon() {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);
		Coupon coupon = new Coupon(0.1);
		order.addCoupon(coupon);
		CouponAndMemberShipDiscountCalculationService service = new CouponAndMemberShipDiscountCalculationService();
		MemberGrade grade = MemberGrade.vip();

		order.calculateAmounts(service, grade);

		Assertions.assertThat(order.getPaymentAmounts()).isEqualTo(new Money(1600));
	}

	@Test
	void calculateAmounts_whenPassDiscountCalculationService() {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);
		Map<MemberId, String> rules = Map.of(orderer.getMemberId(), "10PERCENT");
		DiscountCalculationService service = new RuleBasedDiscountCalculationService(rules);

		order.calculateAmounts(service);

		Assertions.assertThat(order.getPaymentAmounts()).isEqualTo(new Money(1800));
	}

	@DisplayName("주문을 취소하고 환불한다")
	@Test
	void cancelOrderAndRefund() {
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);

		RefundService refundService = new RefundService();
		order.cancel(refundService);

		assertEquals(OrderState.CANCELED, order.getState());
	}
}
