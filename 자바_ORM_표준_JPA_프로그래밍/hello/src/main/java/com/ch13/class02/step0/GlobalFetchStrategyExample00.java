package com.ch13.class02.step0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class GlobalFetchStrategyExample00 implements ApplicationRunner {

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

		String memberName = orderController.view(order.getId());
		log.info("memberName is {}", memberName);
	}

	public static void main(String[] args) {
		SpringApplication.run(GlobalFetchStrategyExample00.class, args);
	}
}
