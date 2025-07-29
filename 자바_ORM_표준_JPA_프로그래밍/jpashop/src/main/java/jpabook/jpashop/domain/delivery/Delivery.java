package jpabook.jpashop.domain.delivery;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.order.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Delivery {
	@Id
	@GeneratedValue
	@Column(name = "DELIVERY_ID")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery", cascade = CascadeType.ALL)
	private Order order;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;

	@Builder
	public Delivery(Long id, Address address, DeliveryStatus status) {
		this.id = id;
		this.address = address;
		this.status = status;
	}

	public static Delivery ready(Address address) {
		return Delivery.builder()
			.address(address)
			.status(DeliveryStatus.READY)
			.build();
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public boolean isComplete() {
		return status == DeliveryStatus.COMP;
	}
}
