package com.ch7._06_combination_key_identity_relation_mapping.idclass;

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
		emf = Persistence.createEntityManagerFactory("jpabook_combination_key_identity_relation_mapping_idclass");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("복합키 식별 관계 매핑 테스트")
	public void test() {
		// given
		Parent parent = new Parent("parent1", "parent");
		Child child = new Child(parent, "child1", "child");
		GrandChild grandChild = new GrandChild(child, "grandChild1", "grandChild");
		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(parent);
		em.persist(child);
		em.persist(grandChild);
		em.flush();
		em.clear();
		// then
		ChildId childId = new ChildId("parent1", "child1");
		GrandChildId grandChildId = new GrandChildId(childId, "grandChild1");

		Parent findParent = em.find(Parent.class, "parent1");
		Child findChild = em.find(Child.class, childId);
		GrandChild findGrandChild = em.find(GrandChild.class, grandChildId);

		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findParent.getId()).isEqualTo("parent1");
		assertions.assertThat(findParent.getName()).isEqualTo("parent");
		assertions.assertThat(findChild.getParent()).isEqualTo(findParent);
		assertions.assertThat(findChild.getChildId()).isEqualTo("child1");
		assertions.assertThat(findChild.getName()).isEqualTo("child");
		assertions.assertThat(findGrandChild.getChild()).isEqualTo(findChild);
		assertions.assertThat(findGrandChild.getId()).isEqualTo("grandChild1");
		assertions.assertThat(findGrandChild.getName()).isEqualTo("grandChild");

		//rollback
		transaction.rollback();
	}
}
