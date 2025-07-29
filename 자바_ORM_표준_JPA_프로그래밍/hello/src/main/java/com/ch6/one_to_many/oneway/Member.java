package com.ch6.one_to_many.oneway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 회원과 팀은 다대일 양방향 관계에 있습니다.
 * <p>
 * 연관관계의 주인은 다 쪽인 회원입니다.
 * <p>
 * 회원과 팀 둘다 서로를 참조하게 하기 위해서 연관관계 메소드를 제공합니다.
 * <p>
 * 회원 엔티티의 경우에는 setTeam, 팀 엔티티의 경우에는 addMembers 연관관계 메소드가 있습니다.
 * <p>
 * 연관관계 메소드는 한곳에만 작성하는 것이 안전합니다. 양쪽다 작성하는 경우 무한루프에 빠질 위험이 있으며 이를 예방하기 위해서는
 * <p>
 * 조건문이 필요합니다. 따라서 양방향 관계에 있는 엔티티의 연관관계 메소드는 어느 한쪽만 호출하면 됩니다.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Entity
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "MEMBER_ID")
	private Long id;

	private String username;
}
