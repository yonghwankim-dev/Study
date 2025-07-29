package com.ch6.many_to_many.twoway;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Product {

	@Id
	@GeneratedValue
	@Column(name = "PRODUCT_ID")
	private Long id;

	private String name;

	@ManyToMany(mappedBy = "products")
	private List<Member> members = new ArrayList<>();
}
