package com.myshop.member.infrastructure;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberIdGenerator;

@Component
public class UuidMemberIdGenerator implements MemberIdGenerator {
	@Override
	public MemberId generate() {
		String uuid = UUID.randomUUID().toString();
		return new MemberId("member-" + uuid);
	}
}
