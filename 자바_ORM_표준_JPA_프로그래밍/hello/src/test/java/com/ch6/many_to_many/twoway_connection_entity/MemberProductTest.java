package com.ch6.many_to_many.twoway_connection_entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberProductTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_many_to_many_twoway_connection_entity");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("회원과 회원상품이 일대다 양방향 관계, 회원상품과 상품이 다대일 단방향 관계로 주어지고 저장이 되고 탐색되는지 테스트")
	public void test() {
		// given
		Member member = new Member();
		member.setUsername("김용환");

		Product product = new Product();
		product.setName("상품1");

		MemberProduct memberProduct = new MemberProduct();
		memberProduct.setMember(member); // 주문회원 - 연관관계 설정
		memberProduct.setProduct(product); // 주문상품 - 연관관계 설정
		memberProduct.setOrderMount(2); // 주문 수량

		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(member);
		em.persist(product);
		em.persist(memberProduct);
		em.flush();

		MemberProductId memberProductId = new MemberProductId();
		memberProductId.setMember(member.getId());
		memberProductId.setProduct(product.getId());

		MemberProduct findMemberProduct = em.find(MemberProduct.class, memberProductId);
		// then
		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findMemberProduct.getMember()).isEqualTo(member);
		assertions.assertThat(findMemberProduct.getProduct()).isEqualTo(product);
		assertions.assertThat(findMemberProduct.getOrderMount()).isEqualTo(2);
		transaction.rollback();
	}
}
