package com.ch7._06_combination_key_identity_relation_mapping.embeddedid;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GrandChild {
	@EmbeddedId
	private GrandChildId id;

	@MapsId("childId") // GrandChildId.childId 매핑
	@ManyToOne
	@JoinColumns(value = {
		@JoinColumn(name = "PARENT_ID"),
		@JoinColumn(name = "CHILD_ID")
	})
	private Child child;

	private String name;
}
