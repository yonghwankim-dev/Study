package com.myshop.message.application;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.myshop.catalog.query.product.dto.ViewLog;
import com.myshop.message.config.RabbitConfig;

@Service
public class ActivityConsumer {

	@RabbitListener(queues = RabbitConfig.QUEUE_NAME)
	public void receiveMessage(ViewLog viewLog) {
		System.out.println(" [x] Received: " + viewLog);
	}
}
