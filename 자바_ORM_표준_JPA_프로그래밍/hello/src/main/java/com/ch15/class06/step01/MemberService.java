package com.ch15.class06.step01;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
		List<Object[]> resultList = em.createQuery("select m.id, m.name from Member m")
			.getResultList();

		List<Member> members = resultList.stream()
			.map(object -> Member.builder()
				.id((Long)object[0])
				.name((String)object[1])
				.build())
			.collect(Collectors.toList());

		System.out.println(em.contains(members.get(0)));
		System.out.println(em.contains(members.get(1)));
		return members;
	}
}
