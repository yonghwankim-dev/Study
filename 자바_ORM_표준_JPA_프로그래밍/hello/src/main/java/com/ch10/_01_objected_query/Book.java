package com.ch10._01_objected_query;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("B")
@NoArgsConstructor
public class Book extends Item {
	private String author;
	private String isbn;

	public Book(Long id, String name, int price, int stockQuantity, List<CategoryItem> categoryItems,
		String author, String isbn) {
		super(id, name, price, stockQuantity, categoryItems);
		this.author = author;
		this.isbn = isbn;
	}
}
