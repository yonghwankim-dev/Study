package com.myshop.order.ui;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myshop.FixedDomainFactory;
import com.myshop.catalog.domain.category.CategoryId;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.common.model.Email;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import com.myshop.order.application.OrderProduct;
import com.myshop.order.domain.model.Address;
import com.myshop.order.domain.model.Receiver;
import com.myshop.order.domain.model.ShippingInfo;
import com.myshop.order.domain.repository.OrderRepository;
import com.myshop.order.query.dto.OrderRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	private String sessionId;
	private String memberId;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;

		memberId = "member-1";
		String password = "12345";
		Member member = FixedDomainFactory.createMember(memberId);
		memberRepository.save(member);
		Email email = member.getEmails().getEmails().stream().findAny().orElseThrow();
		sessionId = login(email.getAddress(), password);

		productRepository.save(FixedDomainFactory.createProduct(new ProductId("product-1"), new CategoryId(1L)));
		productRepository.save(FixedDomainFactory.createProduct(new ProductId("product-2"), new CategoryId(2L)));
	}

	@AfterEach
	void tearDown() {
		memberRepository.deleteAll();
		productRepository.deleteAll();
		orderRepository.deleteAll();
	}

	private String login(String email, String password) {
		return RestAssured.given()
			.param("email", email)
			.param("password", password)
			.when()
			.post("/member/login")
			.then()
			.log().all()
			.statusCode(HttpStatus.FOUND.value())
			.extract()
			.cookie("JSESSIONID");
	}

	@Test
	void placeOrder() throws JsonProcessingException {
		List<OrderProduct> orderProducts = createOrderProducts();
		MemberId orderMemberId = new MemberId(memberId);
		ShippingInfo shippingInfo = createShippingInfo();
		OrderRequest request = new OrderRequest(orderProducts, orderMemberId, shippingInfo);

		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(objectMapper.writeValueAsString(request))
			.cookie("JSESSIONID", sessionId)
			.when()
			.post("/order/place")
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.body("id", org.hamcrest.Matchers.notNullValue());
	}

	private List<OrderProduct> createOrderProducts() {
		OrderProduct orderProduct1 = new OrderProduct("product-1", 2);
		OrderProduct orderProduct2 = new OrderProduct("product-2", 1);
		return List.of(orderProduct1, orderProduct2);
	}

	private ShippingInfo createShippingInfo() {
		Receiver receiver = new Receiver("홍길동", "010-1234-5678");
		String message = "부재시 경비실에 맡겨주세요.";
		Address address = new Address("서울시 강남구 역삼동", "101동 202호",
			"12345");
		return new ShippingInfo(receiver, message, address);
	}

	@Test
	void placeOrder_whenOrderRequestIsNull_thenResponseBadRequest() {
		RestAssured.given()
			.contentType(ContentType.JSON)
			.cookie("JSESSIONID", sessionId)
			.when()
			.post("/order/place")
			.then()
			.log().all()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	void placeOrder_whenOrderProductsAreNull_thenResponseBadRequest() throws JsonProcessingException {
		List<OrderProduct> orderProducts = null;
		MemberId orderMemberId = new MemberId("member-1");
		ShippingInfo shippingInfo = createShippingInfo();
		OrderRequest request = new OrderRequest(orderProducts, orderMemberId, shippingInfo);

		RestAssured.given()
			.contentType(ContentType.JSON)
			.cookie("JSESSIONID", sessionId)
			.body(objectMapper.writeValueAsString(request))
			.when()
			.post("/order/place")
			.then()
			.log().all()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("[0].field", org.hamcrest.Matchers.equalTo("orderProducts"))
			.body("[0].code", org.hamcrest.Matchers.equalTo("empty"));

	}
}
