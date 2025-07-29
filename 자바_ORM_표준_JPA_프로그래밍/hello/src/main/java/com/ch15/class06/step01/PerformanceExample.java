package com.ch15.class06.step01;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 읽기 전용 쿼리의 성능 최적화 방법1 : 스칼라 타입으로 조회
 * - 엔티티가 아닌 스칼라 타입으로 모든 필드를 조회하는 방법
 * - 스칼라 타입은 영속성 컨텍스트가 결과를 관리하지 않음
 */
@SpringBootApplication
public class PerformanceExample implements ApplicationRunner {

	@Autowired
	private MemberService memberService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		memberService.save(Member.builder().name("홍길동").build());
		memberService.save(Member.builder().name("강감찬").build());
		List<Member> members = memberService.find();
		System.out.println(members);
	}

	public static void main(String[] args) {
		SpringApplication.run(PerformanceExample.class, args);
	}
}
