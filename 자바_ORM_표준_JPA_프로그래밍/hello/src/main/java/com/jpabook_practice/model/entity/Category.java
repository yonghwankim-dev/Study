package com.jpabook_practice.model.entity;

import static javax.persistence.GenerationType.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@TableGenerator(
	name = "CATEGORY_SEQ_GENERATOR",
	table = "JPABOOK_SEQUENCES",
	pkColumnValue = "CATEGORY_SEQ"
)
public class Category {

	@Id
	@GeneratedValue(strategy = TABLE, generator = "CATEGORY_SEQ_GENERATOR")
	@Column(name = "CATEGORY_ID")
	private Long id;

	private String name;

	// 카테고리의 계층 구조를 위한 필드들
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Category parent;

	@OneToMany(mappedBy = "parent")
	private List<Category> child = new ArrayList<>();

	//== 연관관계 메소드==//
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
}
