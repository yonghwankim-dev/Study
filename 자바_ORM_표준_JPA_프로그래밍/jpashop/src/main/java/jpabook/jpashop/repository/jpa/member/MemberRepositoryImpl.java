package jpabook.jpashop.repository.jpa.member;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jpabook.jpashop.domain.member.Member;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Member> findMemberCustom() {
		return em.createQuery("select m from Member m", Member.class).getResultList();
	}
}
