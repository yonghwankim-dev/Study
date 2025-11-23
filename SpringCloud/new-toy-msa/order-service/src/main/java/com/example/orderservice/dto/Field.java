package com.example.orderservice.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Field {
	private String type;
	private boolean optional;
	private String field;

	public Field(String type, boolean optional, String field) {
		this.type = type;
		this.optional = optional;
		this.field = field;
	}
}
