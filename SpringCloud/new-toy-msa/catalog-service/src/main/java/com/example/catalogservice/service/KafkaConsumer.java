package com.example.catalogservice.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.catalogservice.jpa.CatalogEntity;
import com.example.catalogservice.jpa.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
	private final CatalogRepository repository;

	@Transactional
	@KafkaListener(topics = "example-order-topic")
	public void processMessage(String kafkaMessage){
		log.info("Kafka Message: ======>" + kafkaMessage);

		Map<String, Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			map = mapper.readValue(kafkaMessage, new TypeReference<>() {
			});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
		entity.setStock(entity.getStock() - (Integer)map.get("qty"));

		repository.save(entity);
	}
}
