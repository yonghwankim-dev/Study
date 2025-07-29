package com.ch15.class02.step05;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class EntityEqualityExample05 implements ApplicationRunner {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		em.persist(new Member("member1", "김용환"));
		em.flush();
		em.clear();

		Member refMember = em.getReference(Member.class, "member1");

		System.out.println("refMember Type = " + refMember.getClass());

		System.out.println(Member.class == refMember.getClass()); // false
		System.out.println(refMember instanceof Member); // true
	}

	public static void main(String[] args) {
		SpringApplication.run(EntityEqualityExample05.class);
	}
}
