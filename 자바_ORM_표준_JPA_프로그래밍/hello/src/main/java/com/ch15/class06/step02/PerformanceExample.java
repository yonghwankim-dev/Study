package com.ch15.class06.step02;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 읽기 전용 쿼리의 성능 최적화 방법2 : 읽기 전용 쿼리 힌트 사용
 * - org.hibernate.readOnly를 사용하여 엔티티를 읽기 전용으로 조회함
 * - 읽기 전용이기 때문에 영속성 컨텍스트는 스냅샷을 보관하지 않아서 메모리 사용량을 최적화할 수 있음
 * - 스냅샷이 없기 때문에 엔티티를 수정해도 데이터베이스에 반영되지 않음
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
		List<Member> members2 = memberService.find2();
		System.out.println(members);
		System.out.println(members2);
	}

	public static void main(String[] args) {
		SpringApplication.run(PerformanceExample.class, args);
	}
}
