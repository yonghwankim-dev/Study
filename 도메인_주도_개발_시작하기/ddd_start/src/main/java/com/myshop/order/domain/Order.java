package com.myshop.order.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.myshop.common.model.Money;
import com.myshop.coupon.domain.Coupon;
import com.myshop.member.domain.MemberGrade;
import com.myshop.order.domain.discount.DiscountCalculationService;

import jakarta.persistence.Access;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "purchase_order")
@Access(jakarta.persistence.AccessType.FIELD)
public class Order {
	@EmbeddedId
	private OrderNo orderNo;

	@Version
	private long version;
	@Embedded
	private Orderer orderer;

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private OrderState state;
	@Embedded
	private ShippingInfo shippingInfo;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_no"))
	@OrderColumn(name = "line_idx")
	private List<OrderLine> orderLines;

	@Column(name = "total_amounts")
	private Money totalAmounts;

	@Column(name = "order_date")
	private LocalDateTime orderDate;

	private Money paymentAmounts;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "coupon", joinColumns = @JoinColumn(name = "order_no"))
	@OrderColumn(name = "coupon_idx")
	private List<Coupon> coupons;

	protected Order() {
	}

	public Order(OrderNo orderNo, Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfo,
		OrderState state) {
		setOrderNo(orderNo);
		setOrderer(orderer);
		setOrderLines(orderLines);
		setShippingInfo(shippingInfo);
		this.state = state;
		this.orderDate = LocalDateTime.now();
		this.coupons = new ArrayList<>();
	}

	private void setOrderNo(OrderNo id) {
		if (id == null) {
			throw new IllegalArgumentException("no OrderNo");
		}
		this.orderNo = id;
	}

	private void setOrderer(Orderer orderer) {
		if (orderer == null) {
			throw new IllegalArgumentException("no Orderer");
		}
		this.orderer = orderer;
	}

	private void setOrderLines(List<OrderLine> orderLines) {
		verifyAtLeastOneOrMoreOrderLines(orderLines);
		this.orderLines = orderLines;
		calculateTotalAmount();
	}

	private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
		if (orderLines == null || orderLines.isEmpty()) {
			throw new IllegalArgumentException("no OrderLine");
		}
	}

	private void calculateTotalAmount() {
		int sum = orderLines.stream()
			.mapToInt(OrderLine::getAmounts)
			.sum();
		this.totalAmounts = new Money(sum);
	}

	private void setShippingInfo(ShippingInfo shippingInfo) {
		if (shippingInfo == null) {
			throw new IllegalArgumentException("no ShippingInfo");
		}
		this.shippingInfo = shippingInfo;
	}

	public void changeShippingInfo(ShippingInfo newShippingInfo) {
		verifyNotYetShipped();
		setShippingInfo(newShippingInfo);
	}

	private void verifyNotYetShipped() {
		if (state != OrderState.PAYMENT_WAITING && state != OrderState.PREPARING) {
			throw new IllegalStateException("already shipped");
		}
	}

	public void cancel() {
		verifyNotYetShipped();
		this.state = OrderState.CANCELED;
	}

	public void calculateAmounts(DiscountCalculationService service, MemberGrade grade) {
		Money totalAmounts = getTotalAmounts();
		Money discountAmounts = service.calculateDiscountAmounts(this.orderLines, this.coupons, grade);
		this.paymentAmounts = totalAmounts.minus(discountAmounts);
	}

	public void addCoupon(Coupon coupon) {
		this.coupons.add(coupon);
	}

	public OrderNo getOrderNo() {
		return orderNo;
	}

	public Orderer getOrderer() {
		return orderer;
	}

	public Money getTotalAmounts() {
		return totalAmounts;
	}

	public OrderState getState() {
		return state;
	}

	public long getVersion() {
		return version;
	}

	public Money getPaymentAmounts() {
		return paymentAmounts;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		Order order = (Order)object;
		return Objects.equals(orderNo, order.orderNo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderNo);
	}

	@Override
	public String toString() {
		return "Order{" +
			"orderNo=" + orderNo +
			'}';
	}
}
