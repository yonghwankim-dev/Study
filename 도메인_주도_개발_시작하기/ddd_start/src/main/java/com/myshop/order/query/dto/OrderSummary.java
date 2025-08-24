package com.myshop.order.query.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Immutable
@Subselect(
	"""
		select
			o.order_no as number,
			o.version,
			o.orderer_id,
			o.orderer_name,
			o.total_amounts,
			o.receiver_name,
			o.state,
			o.order_date,
			p.product_id,
			p.product_name as product_name
		from purchase_order o inner join order_line ol 
			on o.order_no = ol.order_no
			cross join product p
		where
		ol.line_idx = 0
		and ol.product_id = p.product_id"""
)
@Synchronize({"purchase_order", "order_line", "product"})
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

	public OrderSummary(String number, long version, String ordererId, String ordererName, int totalAmounts,
		String receiverName, String state, LocalDateTime orderDate, String productId, String productName) {
		this.number = number;
		this.version = version;
		this.ordererId = ordererId;
		this.ordererName = ordererName;
		this.totalAmounts = totalAmounts;
		this.receiverName = receiverName;
		this.state = state;
		this.orderDate = orderDate;
		this.productId = productId;
		this.productName = productName;
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

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		OrderSummary that = (OrderSummary)object;
		return Objects.equals(number, that.number);
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}
}
