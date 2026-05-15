package com.example.orderservice.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.jpa.OrderEntity;
import com.example.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	@Override
	public OrderDto createOrder(OrderDto orderDetails) {
		orderDetails.setOrderId(UUID.randomUUID().toString());
		orderDetails.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		OrderEntity orderEntity = modelMapper.map(orderDetails, OrderEntity.class);
		orderRepository.save(orderEntity);

		return modelMapper.map(orderEntity, OrderDto.class);
	}

	@Override
	public OrderDto getOrderByOrderId(String orderId) {
		OrderEntity entity = orderRepository.findByOrderId(orderId);
		return new ModelMapper().map(entity, OrderDto.class);
	}

	@Override
	public Iterable<OrderEntity> getOrdersByUserId(String userId) {
		return orderRepository.findByUserId(userId);
	}
}
