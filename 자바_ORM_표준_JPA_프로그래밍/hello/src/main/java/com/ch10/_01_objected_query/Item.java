package com.ch10._01_objected_query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@NoArgsConstructor
@AllArgsConstructor
public abstract class Item {
	@Id
	@GeneratedValue
	@Column(name = "ITEM_ID")
	private Long id;
	private String name;       // 이름
	private int price;         // 가격
	private int stockQuantity; // 재고수량

	@OneToMany(mappedBy = "item")
	private List<CategoryItem> categoryItems = new ArrayList<>();

	public void addCategoryItem(CategoryItem categoryItem) {
		categoryItems.add(categoryItem);
	}
}
