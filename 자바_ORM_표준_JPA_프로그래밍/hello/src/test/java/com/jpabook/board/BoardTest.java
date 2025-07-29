package com.jpabook.board;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jpabook.board.Board;

class BoardTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("기본키 생성 전략을 IDENTITY로 설정하여 기본키 생성을 데이터베이스에 위임하는지 테스트")
	public void identity() {
		// given
		EntityTransaction transaction = em.getTransaction();
		Board board = new Board();
		// when
		transaction.begin();
		em.persist(board);
		// then
		Board foundBoard = em.find(Board.class, board.getId());
		Assertions.assertThat(foundBoard.getId()).isEqualTo(1L);
		transaction.rollback();
	}
}
