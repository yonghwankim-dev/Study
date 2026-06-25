package com.example.orderservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.orderservice.dto.Field;
import com.example.orderservice.dto.KafkaOrderDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.Payload;
import com.example.orderservice.dto.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final List<Field> fields = Arrays.asList(
		new Field("order_id", "string", true),
		new Field("user_id", "string", true),
		new Field("product_id", "string", true),
		new Field("qty","int32", true),
		new Field("unit_price", "int32", true),
		new Field("total_price", "int32", true)
	);
	private final Schema schema = Schema.builder()
		.fields(fields)
		.type("struct")
		.optional(false)
		.name("orders")
		.build();

	public OrderDto send(String kafkaTopic, OrderDto orderDto){
		Payload payload = Payload.builder()
			.order_id(orderDto.getOrderId())
			.user_id(orderDto.getUserId())
			.product_id(orderDto.getProductId())
			.qty(orderDto.getQty())
			.unit_price(orderDto.getUnitPrice())
			.total_price(orderDto.getTotalPrice())
			.build();

		KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = mapper.writeValueAsString(kafkaOrderDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		kafkaTemplate.send(kafkaTopic, jsonInString);
		log.info("Order Producer send data from Order Microservice : {}", kafkaOrderDto);

		return orderDto;
	}
}
