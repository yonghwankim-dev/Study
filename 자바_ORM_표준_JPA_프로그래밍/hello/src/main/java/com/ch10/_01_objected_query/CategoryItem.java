package com.ch10._01_objected_query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CategoryItem {

	@Id
	@GeneratedValue
	@Column(name = "CATEGORY_ITEM_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
}
