package com.ch6.many_to_many.twoway;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_many_to_many_twoway");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("회원과 상품이 다대다 양방향 관계에 있는 경우 저장되고 역방향으로 탐색되는지 테스트")
	public void test1() {
		// given
		Product product = new Product();
		product.setName("상품1");

		Member member = new Member();
		member.setUsername("김용환");
		member.addProduct(product);

		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(product);
		em.persist(member);
		// then
		Product findProduct = em.find(Product.class, product.getId());
		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findProduct).isEqualTo(product);
		assertions.assertThat(findProduct.getMembers()).contains(member);
		transaction.rollback();
	}
}
