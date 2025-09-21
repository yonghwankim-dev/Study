package com.myshop.order.infrastructure;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.FixedDomainFactory;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberGrade;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.domain.Password;
import com.myshop.order.domain.model.Address;
import com.myshop.order.domain.model.Order;
import com.myshop.order.domain.repository.OrderViewDao;
import com.myshop.order.domain.repository.OrderRepository;
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
		Order order = FixedDomainFactory.createOrder();
		orderRepository.save(order);
	}

	private void saveProduct() {
		productRepository.save(FixedDomainFactory.createProduct());
	}

	private void saveMember() {
		memberId = new MemberId("member-1");
		String name = "james";
		memberAddress = new Address(
			"서울 강남구 역삼동",
			"735-17",
			"06235"
		);
		Password password = new Password("password1234");
		Member member = new Member(memberId, name, memberAddress, password, MemberGrade.basic());
		memberRepository.save(member);
	}

	@AfterEach
	void tearDown() {
		orderRepository.deleteAll();
		productRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@Test
	void canCreated() {
		assertNotNull(orderViewDao);
	}

	@Test
	void shouldReturnOrderViewList() {
		String ordererId = "member-1";

		List<OrderView> orderViews = orderViewDao.selectByOrderer(ordererId);

		assertNotNull(orderViews);
		assertThat(orderViews).hasSize(1);
	}
}
