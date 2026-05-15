package com.example.orderservice.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {
	
	private final OrderService orderService;
	@PostMapping("/{userId}/orders")
	public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId, @RequestBody RequestOrder orderDetails){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);
		orderDto.setUserId(userId);
		OrderDto createDto = orderService.createOrder(orderDto);
		ResponseOrder returnValue = modelMapper.map(createDto, ResponseOrder.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}

	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId){
		Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);
		List<ResponseOrder> result = new ArrayList<>();

		orders.forEach(v->result.add(new ModelMapper().map(v, ResponseOrder.class)));
		return ResponseEntity.ok(result);
	}
}
