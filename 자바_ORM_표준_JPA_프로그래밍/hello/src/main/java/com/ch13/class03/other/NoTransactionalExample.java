package com.ch13.class03.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class NoTransactionalExample implements ApplicationRunner {

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderController orderController;


	@Override
	public void run(ApplicationArguments args) {
		Member member = memberService.save(Member.builder()
			.name("홍길동")
			.build());
		log.info("member 초기화 완료, member={}", member);

		Order order = orderService.save(Order.builder()
			.member(member)
			.build());
		log.info("order 초기화 완료, order={}", order);

		String memberName1 = orderController.view(order.getId());
		log.info("memberName1 = {}", memberName1);
	}

	public static void main(String[] args) {
		SpringApplication.run(NoTransactionalExample.class, args);
	}
}
