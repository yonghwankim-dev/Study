package jpabook.jpashop.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import jpabook.jpashop.domain.delivery.Delivery;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.order_item.OrderItem;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "orders")
@Getter
public class Order {
	@Id
	@GeneratedValue
	@Column(name = "ORDER_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@OneToMany(mappedBy = "order")
	@Builder.Default
	private List<OrderItem> orderItems = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "DELIVERY_ID")
	private Delivery delivery;

	private LocalDateTime orderDate;

	private OrderStatus status; // 주문상태 : [ORDER ,CANCEL]

	@Builder
	public Order(Long id, LocalDateTime orderDate, OrderStatus status) {
		this.id = id;
		this.orderDate = orderDate;
		this.status = status;
	}

	public Order() {

	}

	//==생성 메소드==//
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		Order order = Order.builder()
			.status(OrderStatus.ORDER)
			.orderDate(LocalDateTime.now())
			.build();
		order.setMember(member);
		order.setDelivery(delivery);
		for (OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		return order;
	}

	//==연관관계 메소드==//
	public void setMember(Member member) {
		this.member = member;
		member.getOrders().add(this);
	}

	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}

	public void changeStatus(OrderStatus status) {
		this.status = status;
	}

	//==비즈니스 로직==//
	// 주문취소
	public void cancel() {
		if (delivery.isComplete()) {
			throw new RuntimeException("이미 배송완료된 상품은 취소가 불가능합니다");
		}
		changeStatus(OrderStatus.CANCEL);
		for (OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}

	//==조회 로직==//
	// 전체 주문 가격 조회
	public int getTotalPrice() {
		int totalPrice = 0;
		for (OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
	}
}
