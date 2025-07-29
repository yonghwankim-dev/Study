package jpabook.jpashop.controller.member.request;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberCreateRequest {
	private String name;
	private String city;
	private String street;
	private String zipcode;

	public Member toEntity() {
		return Member.builder()
			.name(name)
			.address(Address.builder()
				.city(city)
				.street(street)
				.zipcode(zipcode)
				.build())
			.build();
	}
}
