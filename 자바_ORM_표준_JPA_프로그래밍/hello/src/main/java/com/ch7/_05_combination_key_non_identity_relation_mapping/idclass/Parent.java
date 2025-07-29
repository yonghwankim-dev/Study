package com.ch7._05_combination_key_non_identity_relation_mapping.idclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(ParentId.class)
public class Parent {

	@Id
	@Column(name = "PARENT_ID1", insertable = false, updatable = false)
	private String id1; // ParentId.id1과 연결
	@Id
	@Column(name = "PARENT_ID2", insertable = false, updatable = false)
	private String id2; // ParentId.id2와 연결

	private String name;
}
