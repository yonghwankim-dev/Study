package com.example.orderservice.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@AllArgsConstructor
public class KafkaOrderDto implements Serializable {
	private Schema schema;
	private Payload payload;
}
