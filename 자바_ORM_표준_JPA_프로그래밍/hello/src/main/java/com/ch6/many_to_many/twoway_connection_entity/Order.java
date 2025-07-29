package com.ch6.many_to_many.twoway_connection_entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Order {

	@Id
	@GeneratedValue
	@Column(name = "ORDER_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member; // MemberProductId.member와 연결

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product; // MemberProductId.product와 연결

	private int orderMount;

	private Date orderDate;
}
