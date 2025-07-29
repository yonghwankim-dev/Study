package com.ch15.class05.step02;

import java.util.List;

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

	public List<Member> find(){
		return em.createQuery("select m from Member m", Member.class)
			.getResultList();
	}
}
