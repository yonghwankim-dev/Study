package com.myshop.member.domain;

import java.util.List;

public interface MemberRepository {
	Member findById(MemberId id);

	List<Member> findByIdIn(String[] blockingIds);

	int countsById(MemberId memberId);

	void save(Member member);

	void delete(Member member);

	void deleteAll();
}
