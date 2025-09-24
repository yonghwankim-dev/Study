package com.myshop.message.infrastructure;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.myshop.catalog.query.product.dto.ViewLog;
import com.myshop.message.domain.MessageClient;

public class RabbitMQClient implements MessageClient {
	private final RabbitTemplate rabbitTemplate;
	private final String exchangeName;
	private final String routingKey;

	public RabbitMQClient(RabbitTemplate rabbitTemplate, String exchangeName, String routingKey) {
		this.rabbitTemplate = rabbitTemplate;
		this.exchangeName = exchangeName;
		this.routingKey = routingKey;
	}

	@Override
	public void send(ViewLog viewLog) {
		// 카탈로그 기준으로 작성한 데이터를 큐에 그대로 보관
		rabbitTemplate.convertAndSend(exchangeName, routingKey, viewLog);
		System.out.println(" [x] Sent: " + viewLog);
	}
}
