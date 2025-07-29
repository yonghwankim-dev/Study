package com.ch7._08_one_to_one_identity_relation_mapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
		emf = Persistence.createEntityManagerFactory("jpabook_one_to_one_identity_relation_mapping");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("Board와 BoardDetail이 일대일 식별 양방향 관계로 주어지고 두 엔티티를 저장할때 BoardDetail의 기본키는 Board의 기본키를 매핑한다.")
	public void test() {
		// given
		Board board = new Board(null, "title", null);
		BoardDetail boardDetail = new BoardDetail(null, board, "content");
		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(board);
		em.persist(boardDetail);
		em.flush();
		em.clear();
		// then
		Board findBoard = em.find(Board.class, board.getId());

		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findBoard.getTitle()).isEqualTo("title");
		assertions.assertThat(findBoard.getBoardDetail().getBoard()).isEqualTo(board);
		assertions.assertThat(findBoard.getBoardDetail().getContent()).isEqualTo("content");

		transaction.rollback();
	}

}
