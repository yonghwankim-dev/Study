package com.ch13.class03.step06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring OSIV 적용
 * Spring OSIV를 적용하면 트랜잭션 범위가 서비스 레이어로만 적용되고 영속성 컨텍스트는 Presentation 범위까지 연장된다
 * 컨트롤러에서 member의 데이터를 수정해도 데이터베이스에 변경이 반영되지 않는다.
 * 트랜잭션 범위가 서비스 레이어로만 되어 있기 때문이다.
 */
@Slf4j
@SpringBootApplication
public class SpringOSIVExample02 implements ApplicationRunner {

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

		Member findMember = memberService.getMember(member.getId());
		log.info("findMember : {}", findMember.getName()); // xxx
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringOSIVExample02.class, args);
	}
}
