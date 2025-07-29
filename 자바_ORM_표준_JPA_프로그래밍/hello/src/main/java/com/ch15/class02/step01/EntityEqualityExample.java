package com.ch15.class02.step01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class EntityEqualityExample implements ApplicationRunner {

	@Autowired
	private MemberService memberService;

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Member member = new Member("kim");
		Long saveId = memberService.join(member);
		Member findMember = memberService.fineOne(saveId);
		System.out.println(member == findMember); // 참조값 비교
	}

	public static void main(String[] args) {
		SpringApplication.run(EntityEqualityExample.class, args);
	}
}
