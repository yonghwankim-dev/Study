package com.ch9._02_embeded_type;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Address {

	private String city;
	private String street;
	private String state;
	@Embedded
	private Zipcode zipcode; // 임베디드 타입 포함
}
