package com.ch13.class03.step02;

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

interface MemberView{
	Long getId();
	String getName();
}

@Entity
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements MemberView{
	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Builder
	public Member(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
}

@Controller
class MemberController{
	@Autowired
	private MemberService memberService;

	public String viewMember(Long id){
		MemberView memberView = memberService.getMember(id);
		return memberView.getName();
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

	public MemberView getMember(Long id){
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
