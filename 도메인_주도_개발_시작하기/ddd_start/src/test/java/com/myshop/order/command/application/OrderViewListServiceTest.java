package com.myshop.order.command.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.FixedDomainFactory;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;
import com.myshop.order.command.domain.model.Order;
import com.myshop.order.command.domain.repository.OrderRepository;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderViewListServiceTest {

	@Autowired
	private OrderViewListService service;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void setUp() {
		Member member = FixedDomainFactory.createMember("member-1");
		memberRepository.save(member);

		Product product = FixedDomainFactory.createProduct();
		productRepository.save(product);

		Order order = FixedDomainFactory.createOrder("order-1", "member-1");
		orderRepository.save(order);
	}

	@AfterEach
	void tearDown() {
		productRepository.deleteAll();
		orderRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void getOrderList() {
		String memberId = "member-1";
		Assertions.assertThat(orderRepository.findAll()).hasSize(1);
		Assertions.assertThat(service.getOrderList(memberId)).hasSize(1);
	}
}
