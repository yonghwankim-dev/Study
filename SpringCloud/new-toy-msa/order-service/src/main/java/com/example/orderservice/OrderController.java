package com.example.orderservice;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.service.KafkaProducer;
import com.example.orderservice.service.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
@Slf4j
public class OrderController {

	private final Environment env;
	private final OrderService orderService;
	private final KafkaProducer kafkaProducer;
	private final OrderProducer orderProducer;

	@GetMapping("/health-check")
	public String status(){
		return String.format("It's Working in Order Service on LOCAL PORT %s (SERVER PORT %s)", env.getProperty("local.server.port"), env.getProperty("server.port"));
	}

	@PostMapping("/{userId}/orders")
	public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
		@RequestBody RequestOrder orderDetails) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);

		OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);
		orderDto.setUserId(userId);


		log.info("Before add order data");
		// jpa
		OrderDto createDto = orderService.createOrder(orderDto);
		ResponseOrder responseOrder = modelMapper.map(createDto, ResponseOrder.class);
		log.info("After add order data");

		// kafka
		// orderDto.setOrderId(java.util.UUID.randomUUID().toString());
		// orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

		// ResponseOrder responseOrder = modelMapper.map(orderDto, ResponseOrder.class);

		// send an order to the kafka
		// kafkaProducer.send("example-order-topic", orderDto);
		// orderProducer.send("orders", orderDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable("userId") String userId) {
		log.info("Before retrieve order data");
		Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT);

		List<ResponseOrder> result = new ArrayList<>();

		orderList.forEach(v->{
			result.add(modelMapper.map(v, ResponseOrder.class));
		});
		log.info("Add retrieve order data");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
