package com.ch7._09_join_table.one_to_one;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParentTest {
	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_join_table_one_to_one");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("PARENT와 CHILD의 일대일 조인 테이블 매핑 테스트")
	public void test() {
		// given
		Parent parent = new Parent(null, "parent", null);
		Child child = new Child(null, "child");
		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(parent);
		em.persist(child);
		em.flush();
		em.clear();
		// then
		Parent findParent = em.find(Parent.class, parent.getId());
		Child findChild = em.find(Child.class, child.getId());

		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findParent.getChild()).isEqualTo(parent);
		assertions.assertThat(findParent.getName()).isEqualTo("parent");
		assertions.assertThat(findChild.getName()).isEqualTo("child");

		transaction.rollback();
	}
}
