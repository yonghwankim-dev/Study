package com.ch15.class04.step05;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("A")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album extends Item {
	private String singer;

	public Album(String name, String singer) {
		super(name);
		this.singer = singer;
	}

	@Override
	public String getTitle() {
		return String.format("[제목:%s, 가수:%s]", getName(), singer);
	}
}
