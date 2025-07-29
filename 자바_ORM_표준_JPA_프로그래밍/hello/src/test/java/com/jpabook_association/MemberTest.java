package com.jpabook_association;

import java.util.List;

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

import com.jpabook_association.Member;
import com.jpabook_association.Team;

class MemberTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_association");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("회원1과 회원2, 팀1이 주어지고 회원들이 팀에 소속될때 회원은 팀을 참조할 수 있다")
	public void test1() {
		// given
		Member member1 = new Member();
		member1.setId("member1");
		member1.setUsername("회원1");

		Member member2 = new Member();
		member2.setId("member2");
		member2.setUsername("회원2");

		Team team1 = new Team();
		team1.setId("team1");
		team1.setName("팀1");
		// when
		member1.setTeam(team1);
		member2.setTeam(team1);
		Team findTeam = member1.getTeam();
		// then
		Assertions.assertThat(findTeam.getId()).isEqualTo(team1.getId());
	}

	@Test
	@DisplayName("회원과 팀을 저장하는 코드")
	public void test2() {
		// given
		Team team1 = new Team();
		team1.setId("team1");
		team1.setName("팀1");

		Member member1 = new Member();
		member1.setId("member1");
		member1.setUsername("회원1");
		member1.setTeam(team1); // 연관관계 설정 member1 -> team1

		Member member2 = new Member();
		member2.setId("member2");
		member2.setUsername("회원2");
		member2.setTeam(team1); // 연관관계 설정 member2 -> team1

		EntityTransaction transaction = em.getTransaction();
		// when
		transaction.begin();
		em.persist(team1);
		em.persist(member1);
		em.persist(member2);
		em.flush();
		// then
		Member findMember = em.find(Member.class, member1.getId());
		Assertions.assertThat(findMember.getTeam().getId()).isEqualTo(team1.getId());
		transaction.rollback();
	}

	@Test
	@DisplayName("JPQL 조인 검색")
	public void test3() {
		// given
		Team team1 = new Team();
		team1.setId("team1");
		team1.setName("팀1");

		Member member1 = new Member();
		member1.setId("member1");
		member1.setUsername("회원1");
		member1.setTeam(team1); // 연관관계 설정 member1 -> team1

		Member member2 = new Member();
		member2.setId("member2");
		member2.setUsername("회원2");
		member2.setTeam(team1); // 연관관계 설정 member2 -> team1

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(member1);
		em.persist(member2);
		em.persist(team1);

		String jpql = "select m from Member m join m.team t where t.name = :teamName";
		// when
		List<Member> resultList = em.createQuery(jpql, Member.class)
			.setParameter("teamName", "팀1")
			.getResultList();
		// then
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(resultList).contains(member1, member2);

		transaction.rollback();
	}

	@Test
	@DisplayName("연관관계를 수정하는 코드")
	public void test4() {
		// given
		Team team1 = new Team();
		team1.setId("team1");
		team1.setName("팀1");

		Team team2 = new Team();
		team2.setId("team2");
		team2.setName("팀2");

		Member member1 = new Member();
		member1.setId("member1");
		member1.setUsername("회원1");
		member1.setTeam(team1); // 연관관계 설정 member1 -> team1

		Member member2 = new Member();
		member2.setId("member2");
		member2.setUsername("회원2");
		member2.setTeam(team1); // 연관관계 설정 member2 -> team1

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(member1);
		em.persist(member2);
		em.persist(team1);
		em.persist(team2);
		// when
		Member findMember = em.find(Member.class, "member1");
		findMember.setTeam(team2);
		em.flush();
		// then
		Assertions.assertThat(findMember.getTeam().getId()).isEqualTo(team2.getId());
		transaction.rollback();
	}

	@Test
	@DisplayName("연관관계를 삭제하는 코드")
	public void test5() {
		// given
		Team team1 = new Team();
		team1.setId("team1");
		team1.setName("팀1");

		Member member1 = new Member();
		member1.setId("member1");
		member1.setUsername("회원1");
		member1.setTeam(team1); // 연관관계 설정 member1 -> team1

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(member1);
		// when
		Member findMember = em.find(Member.class, "member1");
		findMember.setTeam(null); // 연관관계 제거
		em.flush();
		// then
		Assertions.assertThat(findMember.getTeam()).isNull();
		transaction.rollback();
	}

	@Test
	@DisplayName("일대다 방향으로 객체 그래프 탐색")
	public void test6() {
		// given
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		Team team1 = new Team();
		team1.setId("team1");
		team1.setName("팀1");
		em.persist(team1);

		Member member1 = new Member();
		member1.setId("member1");
		member1.setUsername("회원1");
		member1.setTeam(team1); // 연관관계 설정 member1 -> team1
		em.persist(member1);

		Member member2 = new Member();
		member2.setId("member2");
		member2.setUsername("회원2");
		member2.setTeam(team1); // 연관관계 설정 member2 -> team1
		em.persist(member2);
		em.flush(); // 데이터베이스에 임시 반영
		em.clear(); // 1차 캐시에 있는 정보들을 제거
		// when
		Team findTeam = em.find(Team.class, "team1");
		List<Member> members = findTeam.getMembers();
		// then
		Assertions.assertThat(members).contains(member1, member2);
		members.forEach(System.out::println);

		transaction.rollback();
	}

	@Test
	@DisplayName("양방향 연관관계 주의점 : 연관관계 주인에는 값을 입력하지 않고, 주인이 아닌곳에만 값을 입력하는 경우")
	public void test7() {
		// given
		Team team1 = new Team();
		team1.setId("team1");
		team1.setName("팀1");

		Member member1 = new Member();
		member1.setId("member1");
		member1.setUsername("회원1");

		Member member2 = new Member();
		member2.setId("member2");
		member2.setUsername("회원2");

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(team1);
		em.persist(member1);
		em.persist(member2);
		// when
		// 주인이 아닌 곳만 연관관계 설정 (부적절한 행동)
		team1.getMembers().add(member1);
		team1.getMembers().add(member2);
		em.persist(team1);
		em.clear();
		// then
		List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
		// 외래키 TEAM_ID에 team1이 아닌 null인 이유는 연관관계의 주인이 아닌 Team.members에만 값을 저장했기 때문이다.
		members.forEach(member -> Assertions.assertThat(member.getTeam()).isNull());
		transaction.rollback();
	}

	@Test
	@DisplayName("순순한 객체 연관관계")
	public void test8() {
		// given
		Team team1 = new Team();
		team1.setId("team1");
		team1.setName("팀1");

		Member member1 = new Member();
		member1.setId("member1");
		member1.setUsername("회원1");

		Member member2 = new Member();
		member2.setId("member2");
		member2.setUsername("회원2");

		member1.setTeam(team1);
		member2.setTeam(team1);
		// when
		List<Member> members = team1.getMembers();
		// then
		Assertions.assertThat(members).isNotEmpty();
	}

	@Test
	@DisplayName("양방향 모두 관계를 설정")
	public void test9() {
		// given
		Team team1 = new Team();
		team1.setId("team1");
		team1.setName("팀1");

		Member member1 = new Member();
		member1.setId("member1");
		member1.setUsername("회원1");

		Member member2 = new Member();
		member2.setId("member2");
		member2.setUsername("회원2");

		member1.setTeam(team1);
		member2.setTeam(team1);
		team1.getMembers().add(member1);
		team1.getMembers().add(member2);
		// when
		List<Member> members = team1.getMembers();
		// then
		Assertions.assertThat(members).contains(member1, member2);
	}

	@Test
	@DisplayName("JPA로 코드 완성")
	public void test10() {
		// given
		Team team1 = new Team();
		team1.setId("team1");
		team1.setName("팀1");

		Member member1 = new Member();
		member1.setId("member1");
		member1.setUsername("회원1");

		Member member2 = new Member();
		member2.setId("member2");
		member2.setUsername("회원2");

		member1.setTeam(team1);
		member2.setTeam(team1);
		EntityTransaction transaction = em.getTransaction();
		// when
		transaction.begin();
		em.persist(member1);
		em.persist(member2);
		em.persist(team1);
		em.flush();
		em.clear();

		Team findTeam = em.find(Team.class, "team1");
		List<Member> members = findTeam.getMembers();
		// then
		Assertions.assertThat(members).contains(member1, member2);
		transaction.rollback();
	}

	@Test
	@DisplayName("양방향 리팩토링 전체코드")
	public void test11() {
		// given
		Team team1 = new Team();
		team1.setId("team1");
		team1.setName("팀1");

		Member member1 = new Member();
		member1.setId("member1");
		member1.setUsername("회원1");

		Member member2 = new Member();
		member2.setId("member2");
		member2.setUsername("회원2");

		member1.setTeam(team1);
		member2.setTeam(team1);
		EntityTransaction transaction = em.getTransaction();
		// when
		transaction.begin();
		em.persist(member1);
		em.persist(member2);
		em.persist(team1);
		// then
		Assertions.assertThat(team1.getMembers().size()).isEqualTo(2);
		transaction.rollback();
	}

}
