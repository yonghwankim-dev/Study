package com.ch13.class03.step03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class TransactionPerRequestExample03 implements ApplicationRunner {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberController memberController;

	@Override
	public void run(ApplicationArguments args) {
		Member member = memberService.save(Member.builder()
			.name("홍길동")
			.build());
		log.info("member 초기화 완료, member={}", member);

		String memberName = memberController.viewMember(member.getId());
		log.info("memberName : {}", memberName);
	}

	public static void main(String[] args) {
		SpringApplication.run(TransactionPerRequestExample03.class, args);
	}
}
