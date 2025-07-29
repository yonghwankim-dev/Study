package com.ch7._09_join_table.many_to_one;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Child {
	@Id
	@GeneratedValue
	@Column(name = "CHILD_ID")
	private Long id;

	private String name;

	@ManyToOne(optional = false)
	@JoinTable(name = "PARENT_CHILD",
		joinColumns = @JoinColumn(name = "CHILD_ID"),
		inverseJoinColumns = @JoinColumn(name = "PARENT_ID"))
	private Parent parent;
}
