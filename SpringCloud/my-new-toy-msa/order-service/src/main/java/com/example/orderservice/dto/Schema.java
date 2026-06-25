package com.example.orderservice.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Schema {
	private String type;
	private boolean optional;
	private String name;
	private List<Field> fields;
}
