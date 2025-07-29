package jpabook.jpashop.repository.jpa.member;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.member.Member;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("사용자 이름으로 회원을 검색한다")
	@Test
	void findByName() {
		// given
		memberRepository.save(createMember("김용환"));
		// when
		Member findMember = memberRepository.findByName("김용환");
		// then
		assertThat(findMember).isNotNull();
	}

	@DisplayName("사용자 이름으로 회원을 검색한다")
	@Test
	void findByName_v2() {
		// given
		memberRepository.save(createMember("김용환"));
		// when
		List<Member> members = memberRepository.findByName_v2("김용환");
		// then
		assertThat(members).hasSize(1);
	}

	@DisplayName("사용자 이름으로 회원을 검색한다")
	@Test
	void findByName_v3_1() {
		// given
		memberRepository.save(createMember("김용환"));
		// when
		Member members = memberRepository.findByName_v3_1("김용환");
		// then
		assertThat(members).isNotNull();
	}

	@DisplayName("사용자 이름으로 회원을 검색한다")
	@Test
	void findByName_v3_2() {
		// given
		memberRepository.save(createMember("김용환"));
		// when
		Member members = memberRepository.findByName_v3_2("김용환");
		// then
		assertThat(members).isNotNull();
	}

	@DisplayName("사용자 이름으로 회원을 검색한다")
	@Test
	void findByName_v4() {
		// given
		memberRepository.save(createMember("김용환"));
		// when
		Member members = memberRepository.findByName_v4("김용환");
		// then
		assertThat(members).isNotNull();
	}

	@DisplayName("사용자 이름으로 회원을 검색한다")
	@Test
	void findByNameWithPage() {
		// given
		memberRepository.save(createMember("김용환"));

		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name").descending());
		// when
		Page<Member> result = memberRepository.findByName("김용환", pageRequest);
		// then
		assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
	}

	@DisplayName("사용자는 회원들을 조회한다")
	@Test
	void findMemberCustom() {
		// given
		memberRepository.save(createMember("김용환"));
		// when
		List<Member> members = memberRepository.findMemberCustom();
		// then
		assertThat(members).hasSize(1);
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
