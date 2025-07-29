package jpabook.jpashop.repository.jpa.member;

import java.util.List;

import jpabook.jpashop.domain.member.Member;

public interface MemberRepositoryCustom {
	List<Member> findMemberCustom();
}
