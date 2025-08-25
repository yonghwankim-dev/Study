package com.myshop.member.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;

@Service
public class BlockMembersService {

	private final MemberRepository memberRepository;

	public BlockMembersService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional
	public void blockMembers(String[] blockingIds) {
		if (blockingIds == null || blockingIds.length == 0) {
			return;
		}
		List<Member> members = memberRepository.findByIdIn(blockingIds);
		for (Member member : members) {
			member.block();
		}
	}
}
