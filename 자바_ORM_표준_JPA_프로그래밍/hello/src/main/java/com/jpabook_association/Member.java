package com.jpabook_association;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
@Entity
public class Member {

	@Id
	@Column(name = "MEMBER_ID")
	private String id;

	private String username;

	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team; // 팀의 참조를 보관

	public void setTeam(Team team) {
		// 기존 팀과 관계를 제거
		if (this.team != null) {
			this.team.getMembers().remove(this);
		}
		this.team = team;
		if (this.team != null) {
			this.team.getMembers().add(this);
		}
	}
}
