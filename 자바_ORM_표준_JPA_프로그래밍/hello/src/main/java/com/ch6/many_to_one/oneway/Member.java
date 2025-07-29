package com.ch6.many_to_one.oneway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Member와 Team간의 다대일 단방향 관계
 * <p>
 * 회원과 팀의 다대일 관계에서 단뱡향 관계 다쪽인 Member 엔티티가 일쪽인 Team 엔티티를 일방적으로 참조하는 관계입니다.
 * <p>
 * 회원은 Member.team으로 Team 엔티티를 참조할 수 있지만 반대로 Team은 회원을 참조하는 필드가 없습니다.
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

	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team; // 팀의 참조를 보관

	public void setTeam(Team team) {
		this.team = team;
	}
}
