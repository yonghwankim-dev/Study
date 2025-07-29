package com.jpabook_practice.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Entity
@TableGenerator(
	name = "MEMBER_SEQ_GENERATOR",
	table = "JPABOOK_SEQUENCES",
	pkColumnValue = "MEMBER_SEQ"
)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
	@Column(name = "MEMBER_ID")
	private Long id;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "member")
	@Builder.Default
	private List<Order> orders = new ArrayList<>();
}

