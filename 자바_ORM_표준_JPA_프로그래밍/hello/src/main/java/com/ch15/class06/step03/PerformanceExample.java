package com.ch15.class06.step03;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 읽기 전용 쿼리의 성능 최적화 방법3 : 읽기 전용 트랜잭션 사용
 * - 스프링 프레임워크가 하이버네이트 세션의 플러시 모드를 MANUAL로 설정합니다.
 * - 강제로 플러시 하지 않는 한 플러시가 일어나지 않는다
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
