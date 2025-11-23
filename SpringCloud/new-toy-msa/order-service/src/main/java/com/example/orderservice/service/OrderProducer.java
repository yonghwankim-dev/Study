package com.example.orderservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.orderservice.dto.Field;
import com.example.orderservice.dto.KafkaOrderDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.Payload;
import com.example.orderservice.dto.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderProducer {
	private KafkaTemplate<String, String> kafkaTemplate;

	private List<Field> fields = Arrays.asList(
		new Field("string", true, "order_id"),
		new Field("string", true, "user_id"),
		new Field("string", true, "product_id"),
		new Field("int32", true, "qty"),
		new Field("int32", true, "total_price"),
		new Field("int32", true, "unit_price")
	);
	private Schema schema = Schema.builder()
		.type("struct")
		.fields(fields)
		.optional(false)
		.name("orders")
		.build();

	public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public OrderDto send(String kafkaTopic, OrderDto orderDto){
		Payload payload = Payload.builder()
			.order_id(orderDto.getOrderId())
			.user_id(orderDto.getUserId())
			.product_id(orderDto.getProductId())
			.qty(orderDto.getQty())
			.total_price(orderDto.getTotalPrice())
			.unit_price(orderDto.getUnitPrice())
			.build();

		KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonInString= "";
		try {
			jsonInString = objectMapper.writeValueAsString(kafkaOrderDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		kafkaTemplate.send(kafkaTopic, jsonInString);
		log.info("Order Producer sent data from the Order Service: " + kafkaOrderDto);

		return orderDto;
	}
}
