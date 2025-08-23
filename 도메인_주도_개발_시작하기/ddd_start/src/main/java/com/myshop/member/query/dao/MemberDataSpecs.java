package com.myshop.member.query.dao;

import org.springframework.data.jpa.domain.Specification;

import com.myshop.member.query.dto.MemberData;

public class MemberDataSpecs {

	public static Specification<MemberData> nonBlocked() {
		return (root, query, criteriaBuilder) ->
			criteriaBuilder.equal(root.get("blocked"), false);
	}

	public static Specification<MemberData> nameLike(String name) {
		return (root, query, criteriaBuilder) -> {
			if (name == null || name.isEmpty()) {
				return criteriaBuilder.conjunction(); // Always true predicate
			}
			return criteriaBuilder.like(root.get("name"), "%" + name + "%");
		};
	}
}
