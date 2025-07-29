package com.ch10._01_objected_query;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("A")
@NoArgsConstructor
public class Album extends Item {
	private String artist;
	private String etc;

	public Album(Long id, String name, int price, int stockQuantity, List<CategoryItem> categoryItems,
		String artist, String etc) {
		super(id, name, price, stockQuantity, categoryItems);
		this.artist = artist;
		this.etc = etc;
	}
}
