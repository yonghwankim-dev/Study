package com.ch8.cascade;

import static javax.persistence.CascadeType.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity
public class Parent {

	@Id
	@GeneratedValue
	@Column(name = "PARENT_ID")
	private Long id;

	@OneToMany(mappedBy = "parent", cascade = {PERSIST, REMOVE})
	private List<Child> children = new ArrayList<>();

	public void addChild(Child child) {
		children.add(child);
		child.setParent(this);
	}
}
