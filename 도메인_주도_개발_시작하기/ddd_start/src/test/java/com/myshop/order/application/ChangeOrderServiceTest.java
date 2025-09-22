package com.myshop.order.application;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import com.myshop.catalog.domain.product.ProductId;
import com.myshop.common.model.Money;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberGrade;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.domain.Password;
import com.myshop.order.domain.model.Address;
import com.myshop.order.domain.model.Order;
import com.myshop.order.domain.model.OrderLine;
import com.myshop.order.domain.model.OrderNo;
import com.myshop.order.domain.model.OrderState;
import com.myshop.order.domain.model.Orderer;
import com.myshop.order.domain.model.Receiver;
import com.myshop.order.domain.model.ShippingInfo;
import com.myshop.order.domain.repository.OrderRepository;
import com.myshop.order.error.OrderNotFoundException;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChangeOrderServiceTest {

	private OrderNo id;
	@Autowired
	private ChangeOrderService service;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ChangeOrderStateService changeOrderStateService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private SearchOrderService searchOrderService;
	@Autowired
	private TransactionTemplate transactionTemplate;
	private ShippingInfo shippingInfo;

	private ShippingInfo createNewShippingInfo() {
		Receiver newReceiver = new Receiver("Jane Doe", "0987654321");
		String message = "Please deliver to the back door.";
		Address newAddress = new Address("456 Elm St", "New City", "67890");
		return new ShippingInfo(newReceiver, message, newAddress);
	}

	@BeforeEach
	void setUp() {
		id = new OrderNo("12345");
		MemberId memberId = new MemberId("23456");
		Orderer orderer = new Orderer(memberId, "John Doe");
		ProductId productId = new ProductId("9000000112298");
		OrderLine orderLine = new OrderLine(productId, new Money(1000), 2);
		List<OrderLine> orderLines = List.of(orderLine);
		Receiver receiver = new Receiver("John Doe", "1234567890");
		String message = "Please deliver between 9 AM and 5 PM";
		Address address = new Address("123 Main St", "City", "12345");
		shippingInfo = new ShippingInfo(receiver, message, address);

		String name = "John Doe";
		Password password = new Password("password1234");
		Member member = new Member(memberId, name, address, password, MemberGrade.basic());
		memberRepository.save(member);

		Order order = new Order(id, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);
		orderRepository.save(order);
	}

	@AfterEach
	void tearDown() {
		orderRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@Test
	void shouldChangeShippingInfo_whenOrderExists() {
		ShippingInfo newShippingInfo = createNewShippingInfo();

		Assertions.assertDoesNotThrow(() -> service.changeShippingInfo(id, newShippingInfo, false));
	}

	@Test
	void shouldThrow_whenOrderIsNull() {
		OrderNo notExistingOrderNo = new OrderNo("99999");
		ShippingInfo newShippingInfo = createNewShippingInfo();

		Throwable throwable = catchThrowable(
			() -> service.changeShippingInfo(notExistingOrderNo, newShippingInfo, false));

		assertThat(throwable)
			.isInstanceOf(OrderNotFoundException.class)
			.hasMessage("Order not found: " + notExistingOrderNo);
	}

	@Test
	void shouldChangeAddress_whenUseNewShippingAddrAsMemberAddrIsTrue() {
		ShippingInfo newShippingInfo = createNewShippingInfo();
		boolean useNewShippingAddrAsMemberAddr = true;

		Assertions.assertDoesNotThrow(
			() -> service.changeShippingInfo(id, newShippingInfo, useNewShippingAddrAsMemberAddr));
	}

	@DisplayName("운영자가 주문 상태를 변경 중일 때 고객 스레드는 선점 잠금으로 인하여 배송지를 변경하지 못한다.")
	@Test
	void shouldFailToChangeShippingInfoDueToPessimisticLock() throws InterruptedException {
		ShippingInfo newShippingInfo = createNewShippingInfo();

		CountDownLatch operatorLockAcquired = new CountDownLatch(1);

		Thread operatorThread = new Thread(() -> {
			transactionTemplate.execute(status -> {
				Order order = searchOrderService.search(id);
				operatorLockAcquired.countDown(); // 운영자가 잠금을 획득했음을 알림
				changeOrderStateService.changeOrderState(order, OrderState.SHIPPED);
				return null;
			});
		});

		Thread customerThread = new Thread(() -> {
			try {
				operatorLockAcquired.await(); // 운영자가 잠금을 획득할 때까지 대기

				transactionTemplate.execute(status -> {
					Throwable throwable = catchThrowable(() -> service.changeShippingInfo(id, newShippingInfo, false));
					org.assertj.core.api.Assertions.assertThat(throwable)
						.isInstanceOf(IllegalStateException.class)
						.hasMessage("already shipped");
					return null;
				});
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		operatorThread.start();
		customerThread.start();

		operatorThread.join();
		customerThread.join();

		Order findOrder = searchOrderService.search(id);
		org.assertj.core.api.Assertions.assertThat(findOrder.getState())
			.isEqualTo(OrderState.SHIPPED);
		org.assertj.core.api.Assertions.assertThat(findOrder.getShippingInfo())
			.isEqualTo(shippingInfo);
	}
}
