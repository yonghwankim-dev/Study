package com.ch13.class03.step01;

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

@Entity
@Getter
@Setter
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

@Controller
class MemberController{
	@Autowired
	private MemberService memberService;

	public String viewMember(Long id){
		Member member = memberService.getMember(id);
		member.setName("XXX"); // 보안상의 이유로 고객 이름을 XXX로 변경한다
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

	@Transactional(readOnly = true)
	public Member getMember(Long id){
		return memberRepository.findOne(id);
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
}
