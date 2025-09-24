package com.myshop.message.config;

import java.util.List;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.myshop.message.infrastructure.RabbitMQClient;

@Configuration
public class RabbitConfig {

	public static final String QUEUE_NAME = "user.activity.queue";
	public static final String EXCHANGE_NAME = "user.activity.exchange";
	public static final String ROUTING_KEY = "user.activity";

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, true);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(EXCHANGE_NAME);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}

	@Bean
	public RabbitMQClient rabbitMQClient(RabbitTemplate rabbitTemplate) {
		return new RabbitMQClient(rabbitTemplate, EXCHANGE_NAME, ROUTING_KEY);
	}

	@Bean
	public SimpleMessageConverter messageConverter() {
		SimpleMessageConverter converter = new SimpleMessageConverter();
		converter.setAllowedListPatterns(List.of("com.myshop.catalog.query.product.dto.*"));
		return converter;
	}
}
