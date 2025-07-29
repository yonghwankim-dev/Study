package com.ch7._05_combination_key_non_identity_relation_mapping.idclass;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id1", "id2"})
public class ParentId implements Serializable {
	private String id1; // Parent.id1 매핑
	private String id2; // Parent.id2 매핑
}
