package com.ch14.class04.step01;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	@Transactional
	public Member save(Member member){
		return memberRepository.save(member);
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
}
