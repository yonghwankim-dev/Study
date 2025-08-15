package com.myshop.member.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MemberId implements Serializable {
	@Column(name = "member_id")
	private String id;

	protected MemberId() {
	}

	public MemberId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		MemberId memberId = (MemberId)object;
		return Objects.equals(id, memberId.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
