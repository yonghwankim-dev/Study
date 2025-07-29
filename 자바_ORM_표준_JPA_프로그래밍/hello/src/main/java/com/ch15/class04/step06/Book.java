package com.ch15.class04.step06;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book extends Item {
	private String author;

	public Book(String name, String author) {
		super(name);
		this.author = author;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
