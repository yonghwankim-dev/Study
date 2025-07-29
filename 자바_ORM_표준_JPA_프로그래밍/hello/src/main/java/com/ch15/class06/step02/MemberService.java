package com.ch15.class06.step02;

import java.util.List;
import java.util.stream.Collectors;

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
		List<Member> members = em.createQuery("select m from Member m", Member.class)
			.setHint("org.hibernate.readOnly", true)
			.getResultList();
		members.get(0).setName("김모모");
		return members;
	}

	public List<Member> find2(){
		return em.createQuery("select m from Member m", Member.class)
			.setHint("org.hibernate.readOnly", true)
			.getResultList();
	}
}
