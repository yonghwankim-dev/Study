package com.ch15.class03.step01;

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
		Member member = new Member("member1", "김용환");
		em.persist(member);
		em.flush();
		em.clear();

		Member newMember = new Member("member1", "김용환");
		Member refMember = em.getReference(Member.class, "member1");

		System.out.println(newMember.equals(refMember)); // false
	}

	public static void main(String[] args) {
		SpringApplication.run(ProxyEqualityExample01.class);
	}
}
