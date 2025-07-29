package com.ch15.class05.step03;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 지연로딩 N+1문제
 * 연관 엔티티를 지연 로딩 전략으로 가져오고 연관 엔티티를 실제 조회시 N+1 문제가 발생한다
 */
@SpringBootApplication
public class LazyAndNPlus1Example03 implements ApplicationRunner {

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Member member = Member.builder().build();
		memberService.save(member);
		Member member2 = Member.builder().build();
		memberService.save(member2);

		Order order = Order.builder()
			.member(member)
			.build();
		orderService.save(order);
		Order order2 = Order.builder()
			.member(member2)
			.build();
		orderService.save(order2);

		List<Member> members = memberService.find();
		System.out.println(members);
	}

	public static void main(String[] args) {
		SpringApplication.run(LazyAndNPlus1Example03.class, args);
	}
}
