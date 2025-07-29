package com.ch6.many_to_one.twoway;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "members")
@Entity
public class Team {

	@Id
	@GeneratedValue
	@Column(name = "TEAM_ID")
	private Long id;
	private String name;

	@OneToMany(mappedBy = "team")
	@Builder.Default
	private List<Member> members = new ArrayList<>();

	public void addMember(Member member) {
		this.members.add(member);
		if (member.getTeam() != this) { // 무한루프에 빠지지 않도록 체크
			member.setTeam(this);
		}
	}
}
