package com.myshop.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MemberId implements java.io.Serializable {
	@Column(name = "member_id")
	private String id;

	protected MemberId() {
	}

	public MemberId(String id) {
		this.id = id;
	}
}
