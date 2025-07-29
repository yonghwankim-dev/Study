package com.ch7._04_mapped_superclass;

import javax.persistence.Entity;

@Entity
public class Seller extends BaseEntity {
	private String shopName;
}
