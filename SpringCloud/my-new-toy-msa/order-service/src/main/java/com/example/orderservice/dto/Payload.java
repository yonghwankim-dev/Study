package com.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Builder
@Data
public class Payload {
	private String order_id;
	private String user_id;
	private String product_id;
	private int qty;
	private int unit_price;
	private int total_price;
}
