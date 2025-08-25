package com.myshop.member.application;

import com.myshop.member.NoMemberException;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;

public final class MemberServiceHelper {
	public static Member findExistingMember(MemberRepository repository, String memberId) {
		Member member = repository.findById(new MemberId(memberId));
		if (member == null) {
			throw new NoMemberException(memberId);
		}
		return member;
	}
}
