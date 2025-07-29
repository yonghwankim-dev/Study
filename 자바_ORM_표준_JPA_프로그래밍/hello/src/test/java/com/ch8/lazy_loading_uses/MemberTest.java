package com.ch8.lazy_loading_uses;

import java.util.ArrayList;
import java.util.List;

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
		emf = Persistence.createEntityManagerFactory("jpabook_proxy_lazy_loading_uses");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("회원과 팀이 다대일, 회원과 주문이 일대다 관계일때 팀은 즉시로딩, 주문은 지연 로딩합니다.")
	public void test() {
		// given
		Team team = new Team(null, "팀1");
		Member member = new Member(null, "김용환", team, new ArrayList<>());
		// when
		Order order1 = new Order(null, null);
		Order order2 = new Order(null, null);
		member.addOrder(order1);
		member.addOrder(order2);
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(team);
		em.persist(member);
		em.persist(order1);
		em.persist(order2);
		em.flush();
		em.clear();
		// then
		/**
		 * 회원엔티티를 참조하는 순간 연관 엔티티 Team이 같이 조회됨
		 * left outer join을 사용한 이유는 Member의 Team이 null일 수 있기 때문
		 * 내부 조인을 사용하기 위해서는 team에 optional=false를 설정해야함
		 * select
		 *         member0_.MEMBER_ID as member_i1_0_0_,
		 *         member0_.TEAM_ID as team_id3_0_0_,
		 *         member0_.username as username2_0_0_,
		 *         team1_.TEAM_ID as team_id1_2_1_,
		 *         team1_.name as name2_2_1_
		 *     from
		 *         Member member0_
		 *     left outer join
		 *         Team team1_
		 *             on member0_.TEAM_ID=team1_.TEAM_ID
		 *     where
		 *         member0_.MEMBER_ID=?
		 */
		Member findMember = em.find(Member.class, member.getId());
		List<Order> findMemberOrders = findMember.getOrders();

		/**
		 * 컬렉션의 Order 엔티티를 참조하는 순간 조회한다. 지연로딩
		 *     select
		 *         orders0_.MEMBER_ID as member_i2_1_0_,
		 *         orders0_.ORDER_ID as order_id1_1_0_,
		 *         orders0_.ORDER_ID as order_id1_1_1_,
		 *         orders0_.MEMBER_ID as member_i2_1_1_
		 *     from
		 *         ORDERS orders0_
		 *     where
		 *         orders0_.MEMBER_ID=?
		 */
		SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(findMemberOrders.size()).isEqualTo(2));
		transaction.rollback();
	}
}
