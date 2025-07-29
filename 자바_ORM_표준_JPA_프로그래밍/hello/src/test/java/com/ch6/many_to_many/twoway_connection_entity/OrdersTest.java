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

class OrdersTest {

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
	@DisplayName("회원과 주문간에 일대다 양방향 관계로 주어지고, 주문과 상품간에 다대일 단방향 관계로 주어질때 주문 엔티티를 저장하고 탐색하는지 테스트")
	public void test1() {
		// given
		Member member = new Member();
		member.setUsername("김용환");

		Product product = new Product();
		product.setName("상품1");

		Order order = new Order();
		order.setMember(member); // 주문 회원 - 연관관계 설정
		order.setProduct(product); // 주문 상품 - 연관관계 설정
		order.setOrderMount(2); // 주문 수량
		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(member);
		em.persist(product);
		em.persist(order);

		Order findOrder = em.find(Order.class, order.getId());
		// then
		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findOrder.getMember()).isEqualTo(member);
		assertions.assertThat(findOrder.getProduct()).isEqualTo(product);
		assertions.assertThat(findOrder.getOrderMount()).isEqualTo(2);
		transaction.rollback();
	}
}
