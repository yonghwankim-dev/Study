package com.ch7._05_combination_key_non_identity_relation_mapping.embeddedid;

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
		emf = Persistence.createEntityManagerFactory(
			"jpabook_combination_key_non_identity_relation_mapping_embedded_id");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("Embeddable 방식으로 복합키 비식별 관계를 보여주는 테스트")
	public void test1() {
		// given
		ParentId parentId = new ParentId("myId1", "myId2");
		Parent parent = new Parent();
		parent.setId(parentId);
		parent.setName("parent");
		// when
		em.persist(parent);
		// then
		Parent findParent = em.find(Parent.class, parentId);
		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findParent.getId().getId1()).isEqualTo("myId1");
		assertions.assertThat(findParent.getId().getId2()).isEqualTo("myId2");
		assertions.assertThat(findParent.getName()).isEqualTo("parent");
	}

	@Test
	@DisplayName("@EmbeddedId가 특정 상황에서는 @IdClass보다 JPQL이 조금더 길어질수 있다")
	public void test2() {
		// given
		ParentId parentId = new ParentId("myId1", "myId2");
		Parent parent = new Parent();
		parent.setId(parentId);
		parent.setName("parent");
		// when
		em.persist(parent);
		// then
		// em.createQuery("select p.id.id1, p.id.id2 from Parent p"); @EmbeddedId가 조금더 김
		// em.createQuery("select p.id1, p.id2 from Parent p"); // @IdClass
	}
}
