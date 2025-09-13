package com.myshop.order.ui;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myshop.member.domain.MemberId;
import com.myshop.order.application.OrderProduct;
import com.myshop.order.domain.Receiver;
import com.myshop.order.domain.ShippingInfo;
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

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	void placeOrder() throws JsonProcessingException {
		List<OrderProduct> orderProducts = createOrderProducts();
		MemberId orderMemberId = new MemberId("member-1");
		ShippingInfo shippingInfo = createShippingInfo();
		OrderRequest request = new OrderRequest(orderProducts, orderMemberId, shippingInfo);

		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(objectMapper.writeValueAsString(request))
			.when()
			.post("/order/place")
			.then()
			.statusCode(HttpStatus.OK.value())
			.body("orderNo", org.hamcrest.Matchers.notNullValue());
	}

	private List<OrderProduct> createOrderProducts() {
		OrderProduct orderProduct1 = new OrderProduct("product-1", 2);
		OrderProduct orderProduct2 = new OrderProduct("product-2", 1);
		return List.of(orderProduct1, orderProduct2);
	}

	private ShippingInfo createShippingInfo() {
		Receiver receiver = new Receiver("홍길동", "010-1234-5678");
		String message = "부재시 경비실에 맡겨주세요.";
		com.myshop.order.domain.Address address = new com.myshop.order.domain.Address("서울시 강남구 역삼동", "101동 202호",
			"12345");
		return new ShippingInfo(receiver, message, address);
	}
}
