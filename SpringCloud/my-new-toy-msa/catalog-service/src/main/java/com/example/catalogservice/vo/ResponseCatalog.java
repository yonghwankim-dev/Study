package com.example.catalogservice.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {
	private String productId;
	private String productName;
	private Integer stock;
	private Integer unitPrice;
	private Date createdAt;
}
