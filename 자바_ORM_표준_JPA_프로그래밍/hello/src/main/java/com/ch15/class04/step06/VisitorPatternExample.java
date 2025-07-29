package com.ch15.class04.step06;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.proxy.HibernateProxy;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class VisitorPatternExample implements ApplicationRunner {

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

		// PrintVisitor
		item.accept(new PrintVisitor());

		// TitleVisitor
		TitleVisitor titleVisitor = new TitleVisitor();
		item.accept(titleVisitor);
		String title = titleVisitor.getTitle();
		log.info("TITLE={}", title);
	}

	public static void main(String[] args) {
		SpringApplication.run(VisitorPatternExample.class);
	}
}
