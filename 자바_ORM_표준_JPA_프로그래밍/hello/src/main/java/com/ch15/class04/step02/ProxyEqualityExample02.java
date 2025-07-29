package com.ch15.class04.step02;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class ProxyEqualityExample02 implements ApplicationRunner {

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

		System.out.println(item.getClass() == Book.class); // false
		System.out.println(item instanceof Book); // false
		System.out.println(item instanceof Item); // true
	}

	public static void main(String[] args) {
		SpringApplication.run(ProxyEqualityExample02.class);
	}
}
