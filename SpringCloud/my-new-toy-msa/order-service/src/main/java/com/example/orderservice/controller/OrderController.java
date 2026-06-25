package com.example.orderservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.service.KafkaProducer;
import com.example.orderservice.service.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order-service")
@Slf4j
public class OrderController {
	
	private final OrderService orderService;
	private final KafkaProducer kafkaProducer;
	private final OrderProducer orderProducer;

	@PostMapping("/{userId}/orders")
	public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder orderDetails){
		log.info("Before add order data");
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);
		orderDto.setUserId(userId);

		// JPA
		OrderDto createDto = orderService.createOrder(orderDto);
		ResponseOrder returnValue = modelMapper.map(createDto, ResponseOrder.class);

		// Kafka
		// orderDto.setOrderId(UUID.randomUUID().toString());
		// orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());
		// ResponseOrder returnValue = modelMapper.map(orderDto, ResponseOrder.class);

		// send an order to the kafka
		// kafkaProducer.send("example-order-topic", orderDto);
		// orderProducer.send("orders", orderDto);

		log.info("After added order data");
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) throws Exception {
		log.info("Before retrieve orders data");
		Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);
		List<ResponseOrder> result = new ArrayList<>();

		orders.forEach(v->result.add(new ModelMapper().map(v, ResponseOrder.class)));
		try{
			Thread.sleep(1000);
			throw new Exception("장애 발생");
		}catch (InterruptedException e){
			log.warn(e.getMessage());
		}
		log.info("After retrieve orders data");

		return ResponseEntity.ok(result);
	}
}
