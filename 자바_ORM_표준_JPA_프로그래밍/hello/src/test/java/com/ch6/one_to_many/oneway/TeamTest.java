package com.ch6.one_to_many.oneway;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_one_to_many_oneway");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	/**
	 * 일대다 단방향 매핑의 단점 : 매핑한 객체가 관리하는 외래 키가 다른 테이블에 있다는 점
	 * <p>
	 * 예를 들어 팀과 회원간에 딜대다 관계를 맺고 팀만이 회원엔티리를 참조하는 단방향 관계인 경우
	 * <p>
	 * 매핑한 객체(회원)가 관리하는 외래 키(TEAM_ID)가 다른 테이블(팀)에 있기 때문에
	 * <p>
	 * 팀 엔티티를 저장할때 Team.members의 참조값을 확인해서 회원 테이블에 있는 TEAM_ID 외래키를 불필요한 업데이트를 하게 됩니다.
	 */
	@Test
	@DisplayName("팀과 회원의 일대다 단방향 관계가 주어질때 일대다 단방향 매핑의 단점을 테스트")
	public void test() {
		// given
		Member member1 = Member.builder().username("김용환").build();
		Member member2 = Member.builder().username("홍길동").build();
		Team team = Team.builder().name("팀1").members(new ArrayList<>()).build();
		team.addMember(member1);
		team.addMember(member2);
		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(member1); // INSERT-member1
		em.persist(member2); // INSERT-member2
		em.persist(team);   // INSERT-team, UPDATE-member1.fk, UPDATE-member2.fk
		// then
		Assertions.assertThat(team.getMembers().size()).isEqualTo(2);

		transaction.rollback();

	}
}
