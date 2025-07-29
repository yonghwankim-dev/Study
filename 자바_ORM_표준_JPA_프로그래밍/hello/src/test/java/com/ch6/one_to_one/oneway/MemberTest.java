package com.ch6.one_to_one.oneway;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ch6.one_to_one.main_table.oneway.Locker;
import com.ch6.one_to_one.main_table.oneway.Member;

class MemberTest {

	@Test
	@DisplayName("회원과 사물함이 일대일 단방향 관계로 주어지고 회원이 사물함을 참조할 수 있는지 테스트")
	public void test() {
		// given
		Member member = Member.builder().username("김용환").build();
		Locker locker = Locker.builder().name("사물함1").build();
		// when
		member.setLocker(locker);
		// then
		Assertions.assertThat(member.getLocker()).isEqualTo(locker);
	}
}
