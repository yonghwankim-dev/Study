package com.ch14.class04.step01;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "B")
public class Book extends Item {
	private String author;
	private String isbn;
}
