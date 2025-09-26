package com.myshop.member.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.myshop.FixedDomainFactory;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberIdGenerator;
import com.myshop.member.domain.MemberRepository;
import com.myshop.member.error.DuplicateIdException;
import com.myshop.member.error.EmptyPropertyException;
import com.myshop.member.query.dto.JoinRequest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JoinServiceTest {

	@Autowired
	private JoinService service;

	@Autowired
	private MemberRepository memberRepository;

	@MockitoSpyBean
	private MemberIdGenerator idGenerator;

	@BeforeEach
	void setUp() {
		MemberId memberId = new MemberId("member-1");
		BDDMockito.given(idGenerator.generate())
			.willReturn(memberId);
	}

	@AfterEach
	void tearDown() {
		memberRepository.deleteAll();
	}

	@Test
	void join() {
		String name = "홍길동";
		String address1 = "서울시 강남구";
		String address2 = "역삼동";
		String zipCode = "12345";
		String email = "hong1234@gamil.com";
		String password = "hong1234@";
		JoinRequest request = new JoinRequest(name, address1, address2, zipCode, email, password);

		MemberId memberId = service.join(request);

		Assertions.assertThat(memberId).isNotNull();
		Assertions.assertThat(memberRepository.findById(memberId)).isNotNull();
	}

	@Test
	void join_whenInvalidName_thenThrowException() {
		String name = "";
		String address1 = "서울시 강남구";
		String address2 = "역삼동";
		String zipCode = "12345";
		String email = "hong1234@gamil.com";
		String password = "hong1234@";
		JoinRequest request = new JoinRequest(name, address1, address2, zipCode, email, password);

		Throwable throwable = Assertions.catchThrowable(() -> service.join(request));

		Assertions.assertThat(throwable)
			.isInstanceOf(EmptyPropertyException.class);
	}

	@Test
	void join_whenDuplicateId_thenThrowException() {
		memberRepository.save(FixedDomainFactory.createMember(idGenerator.generate().getId()));

		String name = "홍길동";
		String address1 = "서울시 강남구";
		String address2 = "역삼동";
		String zipCode = "12345";
		String email = "hong1234@gamil.com";
		String password = "hong1234@";
		JoinRequest request = new JoinRequest(name, address1, address2, zipCode, email, password);

		Throwable throwable = Assertions.catchThrowable(() -> service.join(request));

		Assertions.assertThat(throwable)
			.isInstanceOf(DuplicateIdException.class);
	}
}
