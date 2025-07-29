package com.ch13.class01;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Controller
class HelloController {

	@Autowired
	private HelloService helloService;

	public void hello(){
		// 반환된 member 엔티티는 준영속 상태입니다
		Member member = helloService.logic();
	}
}


@Slf4j
@Service
class HelloService {

	@PersistenceContext // 엔티티 매니저 주입
	private EntityManager em;

	@Autowired
	private Repository1 repository1;
	@Autowired
	private Repository2 repository2;

	// 1. 트랜잭션 시작
	@Transactional
	public Member logic() {
		Member member1 = repository1.fineMember(); // 2. member는 영속상태
		Member member2 = repository2.fineMember();
		log.info("회원 조회 결과 : member1={}", member1);
		log.info("회원 조회 결과 : member2={}", member2);
		return member1;
	}
	// 3. 트랜잭션 종료
}

@Repository
class Repository1{
	@PersistenceContext
	private EntityManager em;

	public Member fineMember(){
		return em.find(Member.class, "id1"); // 영속성 컨텍스트 접근
	}
}

@Repository
class Repository2{
	@PersistenceContext
	private EntityManager em;

	public Member fineMember(){
		return em.find(Member.class, "id2"); // 영속성 컨텍스트 접근
	}
}
