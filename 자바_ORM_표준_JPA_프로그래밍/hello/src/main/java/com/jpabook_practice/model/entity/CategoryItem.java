package com.jpabook_practice.model.entity;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

import com.jpabook_practice.model.entity.item.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@TableGenerator(
	name = "CATEGORY_ITEM_SEQ_GENERATOR",
	table = "JPABOOK_SEQUENCES",
	pkColumnValue = "CATEGORY_ITEM_SEQ"
)
public class CategoryItem {

	@Id
	@GeneratedValue(strategy = TABLE, generator = "CATEGORY_ITEM_SEQ_GENERATOR")
	@Column(name = "CATEGORY_ITEM_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
}
