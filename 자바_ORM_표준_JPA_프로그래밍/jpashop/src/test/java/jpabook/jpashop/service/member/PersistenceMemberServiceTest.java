package jpabook.jpashop.service.member;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.repository.persistence.member.PersistenceMemberRepository;
import jpabook.jpashop.service.persistence.member.PersistenceMemberService;

@Transactional
@SpringBootTest
class PersistenceMemberServiceTest {

	@Autowired
	private PersistenceMemberService persistenceMemberService;

	@Autowired
	private PersistenceMemberRepository persistenceMemberRepository;

	@DisplayName("사용자는 회원을 추가합니다")
	@Test
	void join() {
		// given
		Member member = createMember("홍길동");

		// when
		Long saveId = persistenceMemberService.join(member);

		// then
		Member findMember = persistenceMemberRepository.fineOne(saveId);
		assertThat(findMember).isEqualTo(member);
	}

	@DisplayName("사용자는 중복된 이름으로 회원가입을 할 수 없다")
	@Test
	void joinWithDuplicateMember() {
		// given
		Member member = createMember("홍길동");
		persistenceMemberRepository.save(member);

		Member member2 = createMember("홍길동");

		// when
		Throwable throwable = catchThrowable(() -> persistenceMemberService.join(member2));

		// then
		assertThat(throwable).isInstanceOf(IllegalStateException.class)
			.hasMessage("이미 존재하는 회원입니다");
	}

	private Member createMember(String name) {
		return Member.builder()
			.name(name)
			.address(createAddress())
			.build();
	}

	private Address createAddress() {
		return Address.builder()
			.city("서울")
			.street("강남")
			.zipcode("12345")
			.build();
	}
}
