package com.ch10._01_objected_query;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("M")
@NoArgsConstructor
public class Movie extends Item {
	private String director;
	private String actor;

	public Movie(Long id, String name, int price, int stockQuantity, List<CategoryItem> categoryItems,
		String director, String actor) {
		super(id, name, price, stockQuantity, categoryItems);
		this.director = director;
		this.actor = actor;
	}
}
