package com.myshop.member.query.dao;

import org.springframework.data.jpa.domain.Specification;

import com.myshop.member.query.dto.MemberData;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class MemberNameSpec implements Specification<MemberData> {

	private final String name;

	public MemberNameSpec(String james) {
		this.name = james;
	}

	@Override
	public Predicate toPredicate(Root<MemberData> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return criteriaBuilder.like(root.get("name"), "%" + name + "%");
	}
}
