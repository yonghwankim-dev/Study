package com.ch10._01_objected_query;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"city", "street", "zipcode"})
@Embeddable
public class Address {

	private String city;
	private String street;
	private String zipcode;
}
