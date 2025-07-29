package com.ch15.class05.step01;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class EagerAndNPlus1Example implements ApplicationRunner {

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Member member = Member.builder().build();
		memberService.save(member);

		Order order = Order.builder()
			.member(member)
			.build();
		orderService.save(order);

		Member findMember = memberService.find(member.getId());
		System.out.println(findMember);
	}

	public static void main(String[] args) {
		SpringApplication.run(EagerAndNPlus1Example.class, args);
	}
}
