package com.ch15.class02.step04;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class EntityEqualityExample04 implements ApplicationRunner {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		em.persist(new Member("member1", "김용환"));
		em.flush();
		em.clear();

		Member findMember = em.find(Member.class, "member1");
		Member refMember = em.getReference(Member.class, "member1");

		System.out.println("refMember Type = " + refMember.getClass());
		System.out.println("findMember Type = " + findMember.getClass());

		System.out.println(refMember == findMember);
	}

	public static void main(String[] args) {
		SpringApplication.run(EntityEqualityExample04.class);
	}
}
