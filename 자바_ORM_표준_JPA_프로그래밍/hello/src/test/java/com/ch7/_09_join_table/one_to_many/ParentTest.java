package com.ch7._09_join_table.one_to_many;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
		emf = Persistence.createEntityManagerFactory("jpabook_join_table_one_to_many");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("Parent와 Child가 일대다 관계이고 조인 테이블로 매핑된다.")
	public void test() {
		// given
		Parent parent = new Parent(null, "parent", new ArrayList<>());
		Child child = new Child(null, "child");
		// when
		em.persist(parent);
		em.persist(child);
		// then
		Parent findParent = em.find(Parent.class, parent.getId());
		Child findChild = em.find(Child.class, child.getId());

		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findParent.getChild()).contains(findChild);
		assertions.assertThat(findChild.getName()).isEqualTo("child");
	}
}
