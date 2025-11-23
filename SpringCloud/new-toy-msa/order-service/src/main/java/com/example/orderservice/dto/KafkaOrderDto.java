package com.example.orderservice.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class KafkaOrderDto {
	private Schema schema;
	private Payload payload;

	public KafkaOrderDto(Schema schema, Payload payload) {
		this.schema = schema;
		this.payload = payload;
	}
}
