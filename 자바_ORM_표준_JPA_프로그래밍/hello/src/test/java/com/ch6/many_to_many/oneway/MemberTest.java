package com.ch6.many_to_many.oneway;

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
		emf = Persistence.createEntityManagerFactory("jpabook_many_to_many_oneway");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("회원과 상품이 다대다 단방향 관계에 있는 경우 저장되는지 테스트")
	public void test1() {
		// given
		Product product = new Product();
		product.setName("상품1");

		Member member = new Member();
		member.setUsername("김용환");
		member.getProducts().add(product); // 연관관계 설정
		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(product);
		em.persist(member);
		// then
		Member findMember = em.find(Member.class, member.getId());
		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findMember).isEqualTo(member);
		assertions.assertThat(findMember.getProducts()).contains(product);
		transaction.rollback();
	}

	@Test
	@DisplayName("회원과 상품이 다대다 단뱡향 관계로 주어지고 회원이 가지고 있는 상품이 조회되는지 테스트")
	public void test2() {
		// given
		Product product = new Product();
		product.setName("상품1");

		Member member = new Member();
		member.setUsername("김용환");
		member.getProducts().add(product);
		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(product);
		em.persist(member);
		em.flush();

		Member findMember = em.find(Member.class, member.getId());
		// then
		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findMember.getProducts()).contains(product);
		transaction.rollback();
	}
}
