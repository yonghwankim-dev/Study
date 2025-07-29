package com.ch8.orphan_removal;

import java.util.ArrayList;

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
		emf = Persistence.createEntityManagerFactory("jpabook_proxy_orphan_removal");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("Parent와 Child가 일대다 관계에 있을때 Parent는 Child를 제거했을때 고아객체로써 제거된다.")
	public void orphan_removal() {
		// given
		Parent parent = new Parent(null, new ArrayList<>());
		Child child1 = new Child(null, "child1", null);
		Child child2 = new Child(null, "child1", null);
		parent.addChild(child1);
		parent.addChild(child2);
		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(parent);
		Parent findParent = em.find(Parent.class, parent.getId());
		findParent.getChildren().remove(child1);
		em.flush();
		Child findChild1 = em.find(Child.class, child1.getId());
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(findParent.getChildren().size()).isEqualTo(1);
			softAssertions.assertThat(findChild1).isNull();
		});
		transaction.rollback();
	}
}
