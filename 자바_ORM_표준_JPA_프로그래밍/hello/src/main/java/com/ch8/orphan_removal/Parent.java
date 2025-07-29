package com.ch8.orphan_removal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Parent {

	@Id
	@GeneratedValue
	@Column(name = "PARENT_ID")
	private Long id;

	@OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Child> children = new ArrayList<>();

	public void addChild(Child child) {
		children.add(child);
		child.setParent(this);
	}

}
