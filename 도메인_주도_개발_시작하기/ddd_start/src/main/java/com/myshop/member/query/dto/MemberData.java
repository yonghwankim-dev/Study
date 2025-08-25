package com.myshop.member.query.dto;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "member_data")
public class MemberData {
	@Id
	@Column(name = "member_id")
	private String id;
	@Column(name = "name")
	private String name;
	@Column(name = "blocked")
	private boolean blocked;

	public MemberData() {
	}

	public MemberData(String id, String name, boolean blocked) {
		this.id = id;
		this.name = name;
		this.blocked = blocked;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isBlocked() {
		return blocked;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		MemberData that = (MemberData)object;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
