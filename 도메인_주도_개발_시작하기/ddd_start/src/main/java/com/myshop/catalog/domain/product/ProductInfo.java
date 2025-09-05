package com.myshop.catalog.domain.product;

import com.myshop.common.model.Money;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductInfo {
	@Column(name = "product_name")
	private String productName;

	@Column(name = "price")
	private Money price;

	@Column(name = "detail")
	private String detail;

	protected ProductInfo() {
	}

	public ProductInfo(String productName, Money price, String detail) {
		this.productName = productName;
		this.price = price;
		this.detail = detail;
	}

	public String getProductName() {
		return productName;
	}

	public Money getPrice() {
		return price;
	}

	public String getDetail() {
		return detail;
	}
}
