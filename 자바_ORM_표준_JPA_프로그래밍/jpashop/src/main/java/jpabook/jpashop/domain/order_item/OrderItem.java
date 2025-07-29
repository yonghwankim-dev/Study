package jpabook.jpashop.domain.order_item;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.order.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
	@Id
	@GeneratedValue
	@Column(name = "ORDER_ITEM_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID") // 외래키
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ITEM_ID") // 외래키
	private Item item;

	private int orderPrice; // 주문 가격

	private int count; // 주문 수량

	@Builder
	public OrderItem(Long id, int orderPrice, int count) {
		this.id = id;
		this.orderPrice = orderPrice;
		this.count = count;
	}

	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		OrderItem orderItem = OrderItem.builder()
			.orderPrice(orderPrice)
			.count(count)
			.build();
		orderItem.setItem(item);

		item.removeStock(count);
		return orderItem;
	}

	//==연관 관계 메소드==//
	public void setOrder(Order order) {
		this.order = order;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void cancel() {
		item.addStock(count);
	}

	public int getTotalPrice() {
		return orderPrice * count;
	}
}
