package com.ch7._05_combination_key_non_identity_relation_mapping.embeddedid;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id1", "id2"})
@Embeddable
public class ParentId implements Serializable {

	@Column(name = "PARENT_ID1")
	private String id1;

	@Column(name = "PARENT_ID2")
	private String id2;
}
