package com.ch13.class03.step03;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

class MemberWrapper{
	private Member member;

	public MemberWrapper(Member member) {
		this.member = member;
	}

	// 읽기 전용 메소드만 제공
	public String getName(){
		return member.getName();
	}
}

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

@Controller
class MemberController{
	@Autowired
	private MemberService memberService;

	public String viewMember(Long id){
		MemberWrapper wrapper = memberService.getMember(id);
		return wrapper.getName();
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

	public MemberWrapper getMember(Long id){
		return new MemberWrapper(memberRepository.findOne(id));
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
