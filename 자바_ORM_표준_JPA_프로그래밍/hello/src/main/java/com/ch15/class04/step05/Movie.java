package com.ch15.class04.step05;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
public class Movie extends Item {
	private String director;
	private String actor;

	@Override
	public String getTitle() {
		return String.format("[제목:%s, 감독:%s, 배우:%s]", getName(), director, actor);
	}
}
