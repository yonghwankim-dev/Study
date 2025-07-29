package com.ch10._01_objected_query;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Getter
@NamedQueries({
	@NamedQuery(
		name = "Member.findByUsername",
		query = "SELECT m FROM Member m WHERE m.username = :username"
	),
	@NamedQuery(
		name = "Member.count",
		query = "SELECT count(m) FROM Member m"
	)
})
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(name = "name")
	private String username; // 상태 필드

	private int age; // 상태 필드

	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team; // 연관 필드(단일 값 연관 필드)

	@OneToMany(mappedBy = "member")
	private List<Orders> orders; // 연관 필드 (컬렉션 값 연관 필드)

	public void addOrders(Orders order) {
		orders.add(order);
	}
}
