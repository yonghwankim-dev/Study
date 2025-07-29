package com.ch15.class04.step05;

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
	private String isbn;

	public Book(String name, String author, String isbn) {
		super(name);
		this.author = author;
		this.isbn = isbn;
	}

	@Override
	public String getTitle() {
		return String.format("[제목:%s, 저자:%s]", getName(), author);
	}
}
