package com.myshop.order.domain.model;

import com.myshop.member.domain.MemberId;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Orderer {
	@AttributeOverrides(
		@AttributeOverride(name = "id", column = @Column(name = "orderer_id"))
	)
	private MemberId memberId;

	@Column(name = "orderer_name")
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
