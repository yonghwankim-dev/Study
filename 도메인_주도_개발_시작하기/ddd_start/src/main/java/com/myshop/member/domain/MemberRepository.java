package com.myshop.member.domain;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
	Member findById(MemberId id);

	List<Member> findByIdIn(String[] blockingIds);

	Optional<Member> findByIdForUpdate(MemberId id);

	Optional<Member> findByEmail(String email);

	int countsById(MemberId memberId);

	void save(Member member);

	void delete(Member member);

	void deleteAll();
}
