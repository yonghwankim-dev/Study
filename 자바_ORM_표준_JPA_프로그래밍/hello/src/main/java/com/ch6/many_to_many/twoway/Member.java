package com.ch6.many_to_many.twoway;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	private String username;

	@ManyToMany
	@JoinTable(name = "MEMBER_PRODUCT",
		joinColumns = @JoinColumn(name = "MEMBER_ID"),
		inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
	private List<Product> products = new ArrayList<>();

	public void addProduct(Product product) {
		if (!product.getMembers().contains(this)) {
			product.getMembers().add(this);
		}
		products.add(product);
	}
}
