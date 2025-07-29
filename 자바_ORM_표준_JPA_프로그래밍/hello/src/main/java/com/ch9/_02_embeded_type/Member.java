package com.ch9._02_embeded_type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Member {

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@Embedded
	private Period period;
	@Embedded
	private Address homeAddress; // 임데디드 타입 포함

	@Embedded
	private PhonNumber phonNumber; // 임베디드 타입 포함

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY")),
		@AttributeOverride(name = "street", column = @Column(name = "COMPNAY_STREET")),
		@AttributeOverride(name = "state", column = @Column(name = "COMPANY_STATE"))
	})
	private Address companyAddress;
}
