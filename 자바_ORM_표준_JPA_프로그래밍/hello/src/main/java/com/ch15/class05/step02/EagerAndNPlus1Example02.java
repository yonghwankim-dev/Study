package com.ch15.class05.step02;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 즉시로딩 N+1문제
 * 연관 엔티티를 즉시 로딩 전략으로 가져오고 JPQL 사용시 N+1 문제가 발생합니다.
 */
@SpringBootApplication
public class EagerAndNPlus1Example02 implements ApplicationRunner {

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

		List<Member> members = memberService.find();
		System.out.println(members);
	}

	public static void main(String[] args) {
		SpringApplication.run(EagerAndNPlus1Example02.class, args);
	}
}
