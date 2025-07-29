package com.ch7._06_combination_key_identity_relation_mapping.embeddedid;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class GrandChildId implements Serializable {

	private ChildId childId; // @MapsId("childId")로 매핑

	@Column(name = "GRANDCHILD_ID")
	private String id;
}
