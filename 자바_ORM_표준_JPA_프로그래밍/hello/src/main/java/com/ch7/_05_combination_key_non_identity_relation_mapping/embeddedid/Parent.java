package com.ch7._05_combination_key_non_identity_relation_mapping.embeddedid;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Parent {

	@EmbeddedId
	private ParentId id;

	private String name;
}
