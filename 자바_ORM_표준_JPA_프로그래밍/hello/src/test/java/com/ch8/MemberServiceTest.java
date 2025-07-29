package com.ch8;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ch8.proxy.Member;
import com.ch8.proxy.Team;

class MemberServiceTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_proxy");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("회원과 팀 정보를 출력한다.")
	public void printUserAndTeam() {
		// given
		Team team = new Team("team1", "팀1");
		Member member = new Member("member1", "김용환", team);
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(team);
		em.persist(member);
		em.flush();
		em.clear();
		// when
		Member findMember = em.find(Member.class, "member1");
		Team team2 = member.getTeam();
		// then
		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findMember.getUsername()).isEqualTo("김용환");
		assertions.assertThat(team2.getName()).isEqualTo("팀1");
		assertions.assertAll();
		transaction.rollback();
	}

	@Test
	@DisplayName("회원 정보만 출력한다.")
	public void printUser() {
		Team team = new Team("team1", "팀1");
		Member member = new Member("member1", "김용환", team);
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(team);
		em.persist(member);
		em.flush();
		em.clear();

		Member findMember = em.find(Member.class, "member1");

		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findMember.getUsername()).isEqualTo("김용환");
		assertions.assertAll();
		transaction.rollback();
	}

	@Test
	@DisplayName("프록시 초기화 예제")
	public void getReference() {
		// given
		Team team = new Team("team1", "팀1");
		Member member = new Member("member1", "김용환", team);
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(team);
		em.persist(member);
		em.flush();
		em.clear();
		// when
		Member findMember = em.getReference(Member.class, "member1");
		String username = findMember.getUsername();
		// then
		Assertions.assertThat(username).isEqualTo("김용환");
		transaction.rollback();
	}

}
