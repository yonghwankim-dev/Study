package com.myshop.order.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.myshop.FixedDomainFactory;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;
import com.myshop.order.domain.model.Order;
import com.myshop.order.domain.model.OrderState;
import com.myshop.order.domain.repository.OrderRepository;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderListControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MemberRepository memberRepository;

	private String sessionId;

	private String login(String id, String password) {
		return RestAssured.given()
			.param("memberId", id)
			.param("password", password)
			.when()
			.post("/member/login")
			.then()
			.log().all()
			.statusCode(HttpStatus.FOUND.value())
			.extract()
			.cookie("JSESSIONID");
	}

	@BeforeEach
	void setUp() {
		RestAssured.port = port;

		String memberId = "member-1";
		String password = "12345";
		Member member = FixedDomainFactory.createMember(memberId);
		memberRepository.save(member);

		Product product = FixedDomainFactory.createProduct();
		productRepository.save(product);

		Order order = FixedDomainFactory.createOrder("order-1", "member-1");
		orderRepository.save(order);

		sessionId = login(memberId, password);
	}

	@AfterEach
	void tearDown() {
		productRepository.deleteAll();
		orderRepository.deleteAll();
		memberRepository.deleteAll();
	}

	@Test
	void list() {
		RestAssured.given()
			.cookie("JSESSIONID", sessionId)
			.when()
			.get("/myorders")
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.body("size()", org.hamcrest.Matchers.equalTo(1))
			.body("[0].number", org.hamcrest.Matchers.equalTo("order-1"))
			.body("[0].state", org.hamcrest.Matchers.equalTo(OrderState.PAYMENT_WAITING.name()))
			.body("[0].memberName", org.hamcrest.Matchers.equalTo("홍길동"))
			.body("[0].memberId", org.hamcrest.Matchers.equalTo("member-1"))
			.body("[0].productName", org.hamcrest.Matchers.equalTo("Java Book"));
	}
}
