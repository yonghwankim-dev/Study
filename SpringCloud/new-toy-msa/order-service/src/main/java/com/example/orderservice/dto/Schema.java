package com.example.orderservice.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Schema {
	private String type;
	private List<Field> fields;
	private boolean optional;
	private String name;

	@Builder
	public Schema(String type, List<Field> fields, boolean optional, String name) {
		this.type = type;
		this.fields = fields;
		this.optional = optional;
		this.name = name;
	}
}
