package com.ch7._03_table_per_class_strategy;

import javax.persistence.Entity;

@Entity
public class Book extends Item {
	private String author;  // 작가
	private String isbn;    // ISBN
}
