package com.ch15.class05.step04;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * N+1 문제 해결 방법1 : 페치 조인(fetch join)
 * 엔티티를 조회할때 페치 조인하여 연관 엔티티도 같이 조회하여 N+1 문제를 해결합니다.
 */
@SpringBootApplication
public class LazyAndNPlus1Example04 implements ApplicationRunner {

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
		SpringApplication.run(LazyAndNPlus1Example04.class, args);
	}
}
