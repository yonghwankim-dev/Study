package com.ch14.class03.step03;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class ListenerExample03 implements ApplicationRunner {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Duck duck = new Duck("브라운");
		em.persist(duck);

		duck.setName("찰리");
		em.flush();

		Duck findDuck = em.find(Duck.class, duck.getId());
		System.out.println(findDuck);

		em.remove(duck);
		em.flush();

	}

	public static void main(String[] args) {
		SpringApplication.run(ListenerExample03.class, args);
	}
}

