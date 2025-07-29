package com.jpabook.member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jpabook.member.Member2;

class Member2Test {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("회원 엔티티를 생성하고 getFullName 메소드를 호출하여 firstName과 lastName 프로퍼티에 접근한다")
	public void property() {
		// given
		EntityTransaction transaction = em.getTransaction();
		Member2 member = Member2.builder()
			.firstName("kim")
			.lastName("yonghwan")
			.build();
		// when
		transaction.begin();
		em.persist(member);
		Member2 findMember = em.find(Member2.class, member.getId());
		String fullName = findMember.getFullName();
		// then
		Assertions.assertThat(fullName).isEqualTo("kimyonghwan");
		transaction.rollback();
	}
}
