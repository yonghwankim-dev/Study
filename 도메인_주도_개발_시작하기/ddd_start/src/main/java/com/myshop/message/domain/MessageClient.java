package com.myshop.message.domain;

import com.myshop.catalog.query.product.dto.ViewLog;

public interface MessageClient {
	void send(ViewLog viewLog);
}
