package com.ch13.class03.step06;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Builder
	public Member(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}

@Slf4j
@Controller
class MemberController{
	@Autowired
	private MemberService memberService;

	public String viewMember(Long id){
		Member member = memberService.getMember(id);
		member.setName("xxx");

		Member update = memberService.biz(member);// 트랜잭션 다시 시작되어 변경된 이름이 다시 데이터베이스에 반영됨
		log.info("회원 이름 변경 결과 : {}", update);
		return member.getName();
	}
}

@Service
class MemberService{
	@Autowired
	private MemberRepository memberRepository;

	@Transactional
	public Member save(Member member){
		return memberRepository.save(member);
	}

	@Transactional
	public Member getMember(Long id){
		return memberRepository.findOne(id);
	}

	@Transactional
	public Member biz(Member member) {
		return memberRepository.biz(member);
	}
}

@Repository
class MemberRepository{
	@PersistenceContext
	private EntityManager em;

	public Member save(Member member){
		em.persist(member);
		return member;
	}

	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}

	public Member biz(Member member) {
		return em.merge(member);
	}
}
