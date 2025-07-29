package com.ch15.class04.step03;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class ProxyEqualityExample03 implements ApplicationRunner {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Book saveBook = new Book("jpabook", "kim");
		em.persist(saveBook);
		em.flush();
		em.clear();

		OrderItem orderItem = new OrderItem(saveBook);
		em.persist(orderItem);
		em.flush();
		em.clear();

		OrderItem findOrderItem = em.find(OrderItem.class, orderItem.getId());
		Item item = findOrderItem.getItem();
		Book jpqlBook = em.createQuery("select b from Book b where b.id = :bookId", Book.class)
			.setParameter("bookId", item.getId())
			.getSingleResult();

		System.out.println(jpqlBook.getClass() == Book.class); // true
		System.out.println(jpqlBook instanceof Book); // true
		System.out.println(jpqlBook instanceof Item); // true
	}

	public static void main(String[] args) {
		SpringApplication.run(ProxyEqualityExample03.class);
	}
}
