package com.ch8.cascade;

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
		emf = Persistence.createEntityManagerFactory("jpabook_proxy_cascade");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("Parent와 Child가 일대다 관계일때 Parent가 영속 상태가 될때 그 안에 Child 들도 같이 영속성 전이된다")
	public void persist() {
		// given
		Parent parent = new Parent(null, new ArrayList<>());
		parent.addChild(new Child(null, "child1", null));
		parent.addChild(new Child(null, "child2", null));
		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		/**
		 * Parent가 관리하고 있는 Child 엔티티 객체들도 영속성 전이되어 저장된다.
		 * into Parent(PARENT_ID) values(?)
		 * into Child (name, parent_PARENT_ID, CHILD_ID) values (?, ?, ?)
		 */
		em.persist(parent);
		em.flush();
		em.clear();
		// then
		Parent findParent = em.find(Parent.class, parent.getId());

		/**
		 * select
		 *         children0_.parent_PARENT_ID as parent_p3_0_0_,
		 *         children0_.CHILD_ID as child_id1_0_0_,
		 *         children0_.CHILD_ID as child_id1_0_1_,
		 *         children0_.name as name2_0_1_,
		 *         children0_.parent_PARENT_ID as parent_p3_0_1_
		 *     from
		 *         Child children0_
		 *     where
		 *         children0_.parent_PARENT_ID=?
		 */
		SoftAssertions.assertSoftly(
			softAssertions -> softAssertions.assertThat(findParent.getChildren().size()).isEqualTo(2));
		transaction.rollback();
	}

	@Test
	@DisplayName("Parent와 Child가 일대다 관계인 경우 Parent가 관리하는 Child 삭제시 영속성 전이되어 삭제된다.")
	public void cascade_remove() {
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
		/**
		 * delete from Child where CHILD_ID=?
		 * delete from Child where CHILD_ID=?
		 * delete from Parent where PARENT_ID=?
		 */
		em.remove(parent);
		em.flush();
		// then
		Parent findParent = em.find(Parent.class, parent.getId());
		Child findChild1 = em.find(Child.class, child1.getId());
		Child findChild2 = em.find(Child.class, child2.getId());
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(findParent).isNull();
			softAssertions.assertThat(findChild1).isNull();
			softAssertions.assertThat(findChild2).isNull();
		});
		transaction.rollback();
	}
}
