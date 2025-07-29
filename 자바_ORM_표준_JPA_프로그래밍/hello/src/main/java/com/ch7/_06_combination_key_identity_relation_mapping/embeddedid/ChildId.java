package com.ch7._06_combination_key_identity_relation_mapping.embeddedid;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode(of = {"parentId", "id"})
@NoArgsConstructor
@AllArgsConstructor
public class ChildId implements Serializable {
	private String parentId; // @MpasId("parentId")로 매핑

	@Column(name = "CHILD_ID")
	private String id;
}
