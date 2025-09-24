package com.myshop.message.ui;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myshop.catalog.application.product.ViewLogService;
import com.myshop.message.query.dto.AppendViewLogRequest;

@RestController
public class AppendViewLogController {

	private final ViewLogService viewLogService;

	public AppendViewLogController(ViewLogService viewLogService) {
		this.viewLogService = viewLogService;
	}

	@PostMapping("/view-log/append")
	public void appendViewLog(@RequestBody AppendViewLogRequest request) {
		String memberId = request.getMemberId();
		String productId = request.getProductId();
		LocalDateTime time = LocalDateTime.now();
		viewLogService.appendViewLog(memberId, productId, time);
	}
}
