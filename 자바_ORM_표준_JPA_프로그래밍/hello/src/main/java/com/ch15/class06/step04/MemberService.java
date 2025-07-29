package com.ch15.class06.step04;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



@Service
public class MemberService {
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public Long save(Member member){
		em.persist(member);
		return member.getId();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Member> find(){
		return em.createQuery("select m from Member m", Member.class)
			.getResultList();
	}

	@Transactional(readOnly = true)
	public List<Member> find2(){
		return em.createQuery("select m from Member m", Member.class)
			.setHint("org.hibernate.readOnly", true)
			.getResultList();
	}
}
