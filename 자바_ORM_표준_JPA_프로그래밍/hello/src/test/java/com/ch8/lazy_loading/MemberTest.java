package com.ch8.lazy_loading;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_proxy_lazy_loading");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("회원과 팀이 다대일 관계로 주어지고 회원 엔티티를 참조시 팀 엔티티에 대해서 지연 로딩합니다.")
	public void test() {
		// given
		Team team = new Team(null, "팀1");
		Member member = new Member(null, "김용환", team);
		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(member);
		em.persist(team);
		em.flush();
		em.clear();
		// then
		/**
		 * 회원 엔티티 객체 탐색시 Team 연관 엔티티 객체는 조인하여 가져오지 않음을 알 수 있습니다.
		 * select
		 *         member0_.MEMBER_ID as member_i1_0_0_,
		 *         member0_.TEAM_ID as team_id3_0_0_,
		 *         member0_.username as username2_0_0_
		 *     from
		 *         Member member0_
		 *     where
		 *         member0_.MEMBER_ID=?
		 */
		Member findMember = em.find(Member.class, member.getId());

		/**
		 *     select
		 *         team0_.TEAM_ID as team_id1_1_0_,
		 *         team0_.name as name2_1_0_
		 *     from
		 *         Team team0_
		 *     where
		 *         team0_.TEAM_ID=?
		 */
		Team findTeam = findMember.getTeam(); // 지연로딩 수행
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(findMember).isEqualTo(member);
			softAssertions.assertThat(findTeam).isEqualTo(team);
		});
		transaction.rollback();
	}
}
