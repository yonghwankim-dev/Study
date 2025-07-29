package com.ch15.class03.step01;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Member {
	@Id
	private String id;
	private String name;

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Member member = (Member)object;
		return Objects.equals(name, member.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
