package com.ch7._06_combination_key_identity_relation_mapping.embeddedid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Parent {
	@Id
	@Column(name = "PARENT_ID")
	private String id;

	private String name;
}
