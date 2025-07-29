package com.ch7._07_non_identity_relation_mapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GrandChildTest {
	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_non_identity_relation_mapping");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("비식별 관계로 주어진 Parent, Child, GrandChild 매핑 테스트")
	public void test() {
		// given
		Parent parent = new Parent(null, "parent");
		Child child = new Child(null, parent, "child");
		GrandChild grandchild = new GrandChild(null, child, "grandchild");

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		// when
		em.persist(parent);
		em.persist(child);
		em.persist(grandchild);
		em.flush();
		em.clear();
		// then
		Parent findParent = em.find(Parent.class, parent.getId());
		Child findChild = em.find(Child.class, child.getId());
		GrandChild findGrandChild = em.find(GrandChild.class, grandchild.getId());

		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findParent.getName()).isEqualTo("parent");
		assertions.assertThat(findChild.getParent()).isEqualTo(parent);
		assertions.assertThat(findChild.getName()).isEqualTo("child");
		assertions.assertThat(findGrandChild.getChild()).isEqualTo(child);
		assertions.assertThat(findGrandChild.getName()).isEqualTo("grandChild");

		transaction.rollback();
	}
}
