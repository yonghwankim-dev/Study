package com.ch13.class02.step01;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class GlobalFetchStrategyExample01 implements ApplicationRunner {

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

		Member member2 = memberService.save(Member.builder()
			.name("강감찬")
			.build());
		log.info("member2 초기와 완료, member2={}", member2);
		Order order2 = orderService.save(Order.builder()
			.member(member2)
			.build());
		log.info("order2 초기화 완료, order2={}", order2);

		List<String> memberNames = orderController.viewAll();
		log.info("memberNames is {}", memberNames);

		List<String> memberNames2 = orderController.viewAllUsingFetchJoin();
		log.info("memberNames2 is {}", memberNames2);
	}

	public static void main(String[] args) {
		SpringApplication.run(GlobalFetchStrategyExample01.class, args);
	}
}
