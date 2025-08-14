package com.myshop.member.domain;

public interface MemberRepository {
	Member findById(MemberId id);
	void save(Member member);
	void delete(Member member);
}
