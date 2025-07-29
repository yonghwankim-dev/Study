package com.jpabook_practice.model.entity.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import com.jpabook_practice.model.entity.CategoryItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@TableGenerator(
	name = "ITEM_SEQ_GENERATOR",
	table = "JPABOOK_SEQUENCES",
	pkColumnValue = "ITEM_SEQ"
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ITEM_SEQ_GENERATOR")
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
