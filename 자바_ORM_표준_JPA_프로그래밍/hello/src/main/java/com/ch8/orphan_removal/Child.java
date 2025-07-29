package com.ch8.orphan_removal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Entity
public class Child {

	@Id
	@GeneratedValue
	@Column(name = "CHILD_ID")
	private Long id;

	private String name;

	@ManyToOne
	private Parent parent;

	public void setParent(final Parent parent) {
		this.parent = parent;
	}
}
