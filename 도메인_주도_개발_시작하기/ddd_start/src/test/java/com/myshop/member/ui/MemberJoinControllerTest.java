package com.myshop.member.ui;

import org.assertj.core.api.Assertions;
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
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.query.dto.JoinRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberJoinControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	void join() throws JsonProcessingException {
		String name = "홍길동";
		String address1 = "서울시 강남구";
		String address2 = "역삼동";
		String zipCode = "12345";
		String email = "hong1234@gamil.com";
		String password = "12345";
		JoinRequest request = new JoinRequest(name, address1, address2, zipCode, email, password);

		String memberId = RestAssured.given()
			.contentType(ContentType.JSON)
			.body(objectMapper.writeValueAsString(request))
			.when()
			.post("/member/join")
			.then()
			.log().all()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.body().jsonPath().getString("id");

		Assertions.assertThat(memberRepository.findById(new MemberId(memberId))).isNotNull();
	}

	@Test
	void join_whenEmptyName_thenResponseEmptyCode() throws JsonProcessingException {
		String name = "";
		String address1 = "서울시 강남구";
		String address2 = "역삼동";
		String zipCode = "12345";
		String email = "hong1234@gamil.com";
		String password = "12345";
		JoinRequest request = new JoinRequest(name, address1, address2, zipCode, email, password);

		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(objectMapper.writeValueAsString(request))
			.when()
			.post("/member/join")
			.then()
			.log().all()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("[0].field", org.hamcrest.Matchers.equalTo("name"))
			.body("[0].code", org.hamcrest.Matchers.equalTo("empty"));
	}

	@Test
	void join_whenInvalidName_thenResponseInvalidCode() throws JsonProcessingException {
		String name = "a";
		String address1 = "서울시 강남구";
		String address2 = "역삼동";
		String zipCode = "12345";
		String email = "hong1234@gamil.com";
		String password = "12345";
		JoinRequest request = new JoinRequest(name, address1, address2, zipCode, email, password);

		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(objectMapper.writeValueAsString(request))
			.when()
			.post("/member/join")
			.then()
			.log().all()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("[0].field", org.hamcrest.Matchers.equalTo("name"))
			.body("[0].code", org.hamcrest.Matchers.equalTo("invalid"));
	}

}
