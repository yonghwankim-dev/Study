package com.myshop.order.domain;

import com.myshop.member.domain.MemberId;

import jakarta.persistence.Embeddable;

@Embeddable
public class Orderer{

	private MemberId memberId;
	private String name;

	protected Orderer() {
	}

	public Orderer(MemberId memberId, String name) {
		this.memberId = memberId;
		this.name = name;
	}

	public MemberId getMemberId() {
		return memberId;
	}

	public String getName() {
		return name;
	}
}
