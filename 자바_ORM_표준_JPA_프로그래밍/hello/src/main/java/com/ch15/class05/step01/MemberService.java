package com.ch15.class05.step01;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberService {
	@PersistenceContext
	private EntityManager em;

	public Long save(Member member){
		em.persist(member);
		return member.getId();
	}

	public Member find(Long id){
		return em.find(Member.class, id);
	}
}
