package com.ch14.class02.step01;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class ConverterExample implements ApplicationRunner {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Member member = new Member("김용환", true);
		em.persist(member);

		Member member2 = new Member("홍길동", false);
		em.persist(member2);

		Member findMember = em.find(Member.class, member.getId());
		System.out.println(findMember);

		Member findMember2 = em.find(Member.class, member2.getId());
		System.out.println(findMember2);
	}

	public static void main(String[] args) {
		SpringApplication.run(ConverterExample.class, args);
	}
}
