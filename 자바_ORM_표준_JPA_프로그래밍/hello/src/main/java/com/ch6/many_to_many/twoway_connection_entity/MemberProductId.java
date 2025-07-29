package com.ch6.many_to_many.twoway_connection_entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

// TODO: 왜 Serializable을 구현해야 하는가?
@Getter
@Setter
public class MemberProductId implements Serializable {

	private Long member; // MemberProduct.member와 연결
	private Long product; // MemberProduct.product와 연결

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof MemberProductId)) {
			return false;
		}
		MemberProductId that = (MemberProductId)o;
		return Objects.equals(getMember(), that.getMember()) && Objects.equals(getProduct(),
			that.getProduct());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMember(), getProduct());
	}
}
