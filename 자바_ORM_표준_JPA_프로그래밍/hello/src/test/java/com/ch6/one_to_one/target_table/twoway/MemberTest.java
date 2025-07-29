package com.ch6.one_to_one.target_table.twoway;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

	@Test
	@DisplayName("회원과 사물함이 일대일 양방향 관계로 주어지고 대상 테이블(사물함)에 외래키가 있는 상태에서 서로를 참조할 수 있는지 테스트")
	public void test() {
		// given
		Member member = Member.builder().id(1L).username("김용환").build();
		Locker locker = Locker.builder().id(1L).name("1번").build();
		// when
		member.setLocker(locker);
		// then
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(member.getLocker()).isEqualTo(locker);
		softAssertions.assertThat(locker.getMember()).isEqualTo(member);
	}
}
