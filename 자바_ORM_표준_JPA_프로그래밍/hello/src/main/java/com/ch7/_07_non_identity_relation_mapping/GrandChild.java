package com.ch7._07_non_identity_relation_mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class GrandChild {
	@Id
	@GeneratedValue
	@Column(name = "GRANDCHILD_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "CHILD_ID")
	private Child child;

	private String name;
}
