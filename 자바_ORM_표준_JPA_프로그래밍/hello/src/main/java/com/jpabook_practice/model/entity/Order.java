package com.jpabook_practice.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
@TableGenerator(
	name = "ORDER_SEQ_GENERATOR",
	table = "JPABOOK_SEQUENCES",
	pkColumnValue = "ORDER_SEQ"
)
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ORDER_SEQ_GENERATOR")
	@Column(name = "ORDER_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DELIVERY_ID")
	private Delivery delivery; // 배송정보

	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate; // 주문시간

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus; // 주문상태

	//==연관관계 메소드==//
	public void setMember(Member member) {
		// 기존 연관관계 제거
		if (this.member != null) {
			this.member.getOrders().remove(this);
		}
		this.member = member;
		this.member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}
}
