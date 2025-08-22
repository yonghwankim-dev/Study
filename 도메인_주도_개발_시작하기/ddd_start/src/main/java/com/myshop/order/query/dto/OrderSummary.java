package com.myshop.order.query.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OrderSummary {
	@Id
	private String number;
	private long version;
	@Column(name = "orderer_id")
	private String ordererId;
	@Column(name = "orderer_name")
	private String ordererName;
	@Column(name = "total_amounts")
	private int totalAmounts;
	@Column(name = "receiver_name")
	private String receiverName;
	private String state;
	@Column(name = "order_date")
	private LocalDateTime orderDate;
	@Column(name = "product_id")
	private String productId;
	@Column(name = "product_name")
	private String productName;

	protected OrderSummary() {
	}

	public String getNumber() {
		return number;
	}

	public long getVersion() {
		return version;
	}

	public String getOrdererId() {
		return ordererId;
	}

	public String getOrdererName() {
		return ordererName;
	}

	public int getTotalAmounts() {
		return totalAmounts;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public String getState() {
		return state;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}
}
