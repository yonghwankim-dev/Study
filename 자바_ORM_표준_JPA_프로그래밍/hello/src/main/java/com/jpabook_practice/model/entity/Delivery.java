package com.jpabook_practice.model.entity;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@TableGenerator(
	name = "DELIVERY_SEQ_GENERATOR",
	table = "JPABOOK_SEQUENCES",
	pkColumnValue = "DELIVERY_SEQ"
)
public class Delivery {

	@Id
	@GeneratedValue(strategy = TABLE, generator = "DELIVERY_SEQ_GENERATOR")
	@Column(name = "DELIVERY_ID")
	private Long id;

	@OneToOne(mappedBy = "delivery")
	private Order order;

	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus;
}
