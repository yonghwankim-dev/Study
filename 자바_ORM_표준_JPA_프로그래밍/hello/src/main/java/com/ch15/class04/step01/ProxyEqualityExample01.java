package com.ch15.class04.step01;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class ProxyEqualityExample01 implements ApplicationRunner {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Book saveBook = new Book("jpabook", "kim");
		em.persist(saveBook);
		em.flush();
		em.clear();

		Item proxyItem = em.getReference(Item.class, saveBook.getId());
		System.out.println("proxyItem = " + proxyItem.getClass());

		if (proxyItem instanceof Book){
			System.out.println("proxyItem instanceof Book");
			Book book = (Book)proxyItem;
			System.out.println("책 저자 = " + book.getAuthor());
		}

		System.out.println(proxyItem.getClass() == Book.class); // false
		System.out.println(proxyItem instanceof Book); // false
		System.out.println(proxyItem instanceof Item); // true
	}

	public static void main(String[] args) {
		SpringApplication.run(ProxyEqualityExample01.class);
	}
}
