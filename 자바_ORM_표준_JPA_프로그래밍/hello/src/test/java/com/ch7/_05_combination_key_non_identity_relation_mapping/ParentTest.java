package com.ch7._05_combination_key_non_identity_relation_mapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ch7._05_combination_key_non_identity_relation_mapping.idclass.Child;
import com.ch7._05_combination_key_non_identity_relation_mapping.idclass.Parent;
import com.ch7._05_combination_key_non_identity_relation_mapping.idclass.ParentId;

class ParentTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_combination_key_non_identity_relation_mapping");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("복합키 비식별 관계 매핑 테스트")
	public void test() {
		// given
		Parent parent = new Parent();
		parent.setId1("myId1");
		parent.setId2("myId2");
		parent.setName("parentName");
		// when
		em.persist(parent);
		// then
		ParentId parentId = new ParentId("myId1", "myId2");
		Parent findParent = em.find(Parent.class, parentId);
		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findParent.getId1()).isEqualTo("myId1");
		assertions.assertThat(findParent.getId2()).isEqualTo("myId2");
		assertions.assertThat(findParent.getName()).isEqualTo("parentName");
	}

	@Test
	@DisplayName("Parent 객체가 주어지고 Child 객체를 저장할때 Child 객체의 parent 멤버는 Parent 객체에 복합키로 매핑된다.")
	public void test2() {
		// given
		Parent parent = new Parent();
		parent.setId1("myId1");
		parent.setId2("myId2");
		parent.setName("parentName");
		em.persist(parent);

		Child child = new Child();
		child.setId("myChild1");
		child.setParent(parent);
		// when
		em.persist(child);
		// then
		Child findChild = em.find(Child.class, "myChild1");
		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findChild.getParent().getId1()).isEqualTo("myId1");
		assertions.assertThat(findChild.getParent().getId2()).isEqualTo("myId2");
	}
}
