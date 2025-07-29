package com.ch7._06_combination_key_identity_relation_mapping.idclass;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"child", "id"})
public class GrandChildId implements Serializable {

	private ChildId child; // GrandChild.child 매핑
	private String id;      // GrandChild.id 매핑
}
