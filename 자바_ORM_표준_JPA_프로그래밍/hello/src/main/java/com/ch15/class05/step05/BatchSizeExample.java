package com.ch15.class05.step05;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * N+1 문제 해결 방법2 : BatchSize
 * 연관 엔티티를 조회할때 특정한 개수만큼 IN절을 사용해서 조회하는 방법입니다.
 */
@SpringBootApplication
public class BatchSizeExample implements ApplicationRunner {

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for (int i = 0; i < 10; i++){
			Member member = Member.builder().build();
			memberService.save(member);

			Order order = Order.builder()
				.member(member)
				.build();
			orderService.save(order);
		}
		List<Member> members = memberService.find();
		System.out.println(members);
	}

	public static void main(String[] args) {
		SpringApplication.run(BatchSizeExample.class, args);
	}
}
