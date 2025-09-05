package com.myshop.member.query.dto;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class MemberAuthentication extends User {
	private final String memberId;

	public MemberAuthentication(String username, String password,
		Collection<? extends GrantedAuthority> authorities, String memberId) {
		super(username, password, authorities);
		this.memberId = memberId;
	}

	public String getMemberId() {
		return memberId;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		if (!super.equals(object))
			return false;
		MemberAuthentication that = (MemberAuthentication)object;
		return Objects.equals(memberId, that.memberId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), memberId);
	}
}
