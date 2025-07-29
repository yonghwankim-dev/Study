package com.ch6.many_to_one.twoway;

import java.util.ArrayList;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

	@Test
	@DisplayName("회원과 팀이 다대일 양방향 관계로 주어지고 회원 엔티티가 팀을 설정할때 팀의 회원 리스트에 회원 엔티티가 추가된다.")
	public void test() {
		// given
		Member member = Member.builder()
			.id(1L)
			.username("김용환")
			.build();
		Team team = Team.builder()
			.id(1L)
			.name("팀1")
			.members(new ArrayList<>())
			.build();
		// when
		member.setTeam(team);
		// then
		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(member.getTeam()).isEqualTo(team);
		assertions.assertThat(team.getMembers()).contains(member);
	}
}
