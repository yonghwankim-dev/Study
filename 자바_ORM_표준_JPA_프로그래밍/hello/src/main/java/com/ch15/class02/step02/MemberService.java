package com.ch15.class02.step02;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	@Transactional
	public Long join(Member member){
		memberRepository.save(member);
		return member.getId();
	}

	@Transactional
	public Member fineOne(Long id){
		return memberRepository.fineOne(id);
	}
}

@Repository
class MemberRepository{
	@PersistenceContext
	private EntityManager em;

	public void save(Member member){
		em.persist(member);
	}

	public Member fineOne(Long id){
		return em.find(Member.class, id);
	}
}
