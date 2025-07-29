package com.ch13.class04.step01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class OSIVExample01 implements ApplicationRunner {

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderController orderController;

	@Override
	public void run(ApplicationArguments args) {
		Member member = memberService.save(Member.builder()
			.name("홍길동")
			.build());
		log.info("member 초기화 완료, member={}", member);

		String result = orderController.orderRequest(Order.builder()
			.member(member)
			.build());
		log.info("order result is {}", result);
	}

	public static void main(String[] args) {
		SpringApplication.run(OSIVExample01.class, args);
	}
}
