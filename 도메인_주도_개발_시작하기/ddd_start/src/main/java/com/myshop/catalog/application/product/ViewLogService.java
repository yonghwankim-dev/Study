package com.myshop.catalog.application.product;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.myshop.catalog.query.product.dto.ViewLog;
import com.myshop.message.domain.MessageClient;

@Service
public class ViewLogService {

	private final MessageClient messageClient;

	public ViewLogService(MessageClient messageClient) {
		this.messageClient = messageClient;
	}

	public void appendViewLog(String memberId, String productId, LocalDateTime time) {
		ViewLog viewLog = new ViewLog(memberId, productId, time);
		messageClient.send(viewLog);
	}
}
