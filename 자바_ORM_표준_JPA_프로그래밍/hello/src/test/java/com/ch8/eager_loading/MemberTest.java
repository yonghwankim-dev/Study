package com.ch8.eager_loading;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_practice_eager_loading");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("회원과 팀이 다대일 관계를 맺고 있을때 회원이 팀 객체에 대해서 즉시 로딩 상태라면 회원 객체 탐색시 팀 객체도 같이 탐색된다.")
	public void test() {
		// given
		Team team = new Team(null, "팀1");
		Member member = new Member(null, "김용환", team);
		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(team);
		em.persist(member);
		em.flush();
		em.clear();
		// then
		/**
		 *     select
		 *         member0_.MEMBER_ID as member_i1_0_0_,
		 *         member0_.TEAM_ID as team_id3_0_0_,
		 *         member0_.username as username2_0_0_,
		 *         team1_.TEAM_ID as team_id1_1_1_,
		 *         team1_.name as name2_1_1_
		 *     from
		 *         Member member0_
		 *     left outer join
		 *         Team team1_
		 *             on member0_.TEAM_ID=team1_.TEAM_ID
		 *     where
		 *         member0_.MEMBER_ID=?
		 */
		Member findMember = em.find(Member.class, member.getId());
		transaction.rollback();
	}
}
