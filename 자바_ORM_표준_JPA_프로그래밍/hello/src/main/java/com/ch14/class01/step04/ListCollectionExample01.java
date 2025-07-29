package com.ch14.class01.step04;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class ListCollectionExample01 implements ApplicationRunner {

	@Autowired
	private EntityManager em;

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Board board = new Board("제목1", "내용1");
		em.persist(board);

		Comment comment1 = new Comment("댓글1");
		comment1.setBoard(board);
		board.getComments().add(comment1); // position 0
		em.persist(comment1);

		Comment comment2 = new Comment("댓글2");
		comment2.setBoard(board);
		board.getComments().add(comment2); // position 1
		em.persist(comment2);

		Comment comment3 = new Comment("댓글3");
		comment3.setBoard(board);
		board.getComments().add(comment3); // position 2
		em.persist(comment3);

		Comment comment4 = new Comment("댓글4");
		comment4.setBoard(board);
		board.getComments().add(comment4); // position 3
		em.persist(comment4);

		List<Comment> comments = em.createQuery("select c from Comment c where c.board.id = :boardId", Comment.class)
			.setParameter("boardId", board.getId())
			.getResultList();
		System.out.println("comments : " + comments);
	}

	public static void main(String[] args) {
		SpringApplication.run(ListCollectionExample01.class, args);
	}
}
