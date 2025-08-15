package com.myshop.order.infrastructure;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import com.myshop.order.domain.Address;
import com.myshop.order.domain.Money;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderLine;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;
import com.myshop.order.domain.OrderState;
import com.myshop.order.domain.OrderViewDao;
import com.myshop.order.domain.Orderer;
import com.myshop.order.domain.Product;
import com.myshop.order.domain.ProductId;
import com.myshop.order.domain.ProductRepository;
import com.myshop.order.domain.Receiver;
import com.myshop.order.domain.ShippingInfo;
import com.myshop.order.query.dto.OrderView;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaOrderViewDaoTest {

	@Autowired
	private OrderViewDao orderViewDao;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;
	private MemberId memberId;
	private ProductId productId;
	private Address memberAddress;

	@BeforeEach
	void setUp() {
		saveMember();
		saveProduct();
		saveOrder();
	}

	private void saveOrder() {
		OrderNo orderNo = new OrderNo("1234567890");
		Orderer orderer = new Orderer(memberId, "홍길동");
		Money price = new Money(1000);
		int quantity = 2;
		OrderLine orderLine = new OrderLine(productId, price, quantity);
		List<OrderLine> orderLines = List.of(orderLine);
		Receiver receiver = new Receiver("홍길동", "010-1234-5678");
		ShippingInfo shippingInfo = new ShippingInfo(receiver, memberAddress);
		OrderState state = OrderState.PAYMENT_WAITING;
		Order order = new Order(orderNo, orderer, orderLines, shippingInfo, state);
		orderRepository.save(order);
	}

	private void saveProduct() {
		productId = new ProductId("9000000112298");
		Product product = new Product(productId);
		productRepository.save(product);
	}

	private void saveMember() {
		memberId = new MemberId("12345");
		memberAddress = new Address(
			"서울 강남구 역삼동",
			"735-17",
			"06235"
		);
		Member member = new Member(memberId, memberAddress);
		memberRepository.save(member);
	}

	@Test
	void canCreated(){
		assertNotNull(orderViewDao);
	}

	@Test
	void shouldReturnOrderViewList(){
		String ordererId = "12345";

		List<OrderView> orderViews = orderViewDao.selectByOrderer(ordererId);

		assertNotNull(orderViews);
		assertThat(orderViews).hasSize(1);
	}
}
