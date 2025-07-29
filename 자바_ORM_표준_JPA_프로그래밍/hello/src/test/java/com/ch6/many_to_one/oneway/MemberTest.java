package com.ch6.many_to_one.oneway;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

	@Test
	@DisplayName("다대일 단방향 관계에서 회원이 팀을 참조하는지 테스트")
	public void test() {
		// given
		Member member = Member.builder()
			.id(1L)
			.username("김용환")
			.build();
		Team team = Team.builder()
			.id(1L)
			.name("팀1")
			.build();
		// when
		member.setTeam(team);
		// then
		assertThat(member.getTeam()).isEqualTo(team);
	}
}
