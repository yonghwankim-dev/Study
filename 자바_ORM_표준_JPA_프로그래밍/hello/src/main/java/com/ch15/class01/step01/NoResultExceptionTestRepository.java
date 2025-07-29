package com.ch15.class01.step01;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class NoResultExceptionTestRepository {
	@PersistenceContext
	private EntityManager em;

	public Member findMember(){
		return em.createQuery("select m from Member m", Member.class)
			.getSingleResult();
	}

	public Member fineMember2() throws NoResultException {
		return em.createQuery("select m from Member m", Member.class)
			.getSingleResult();
	}
}
