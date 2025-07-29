package com.ch7._10_secondary_table;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_secondary_table");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("엔티티 하나에 여러 테이블 매핑한다.")
	public void test() {
		// given
		Board board = new Board(null, "제목", "내용");
		// when
		em.persist(board);
		// then
		Board findBoard = em.find(Board.class, board.getId());

		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findBoard.getTitle()).isEqualTo("제목");
		assertions.assertThat(findBoard.getContent()).isEqualTo("내용");
	}

}
