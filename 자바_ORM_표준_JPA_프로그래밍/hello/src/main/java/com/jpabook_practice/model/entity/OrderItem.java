package com.jpabook_practice.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.jpabook_practice.model.entity.item.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ORDER_ITEM")
@TableGenerator(
	name = "ORDER_ITEM_SEQ_GENERATOR",
	table = "JPABOOK_SEQUENCES",
	pkColumnValue = "ORDER_ITEM_SEQ"
)
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ORDER_ITEM_SEQ_GENERATOR")
	@Column(name = "ORDER_ITEM_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_ID")
	private Item item;

	private int orderPrice; // 주문 가격

	private int count; // 주문 수량
}
