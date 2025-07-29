package com.ch14.class01.step05;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToMany(mappedBy = "team")
	@OrderBy(value = "username desc, id asc")
	private Set<Member> members = new HashSet<>();

	public Team(String name) {
		this.name = name;
	}
}

@Service
class TeamService{

	@Autowired
	private TeamRepository teamRepository;

	@Transactional
	public Team save(Team team){
		return teamRepository.save(team);
	}

	@Transactional
	public Team fineOne(Long id){
		return teamRepository.fineOne(id);
	}

	@Transactional
	public void printMembers(Long id) {
		Team fineTeam = fineOne(id);
		List<Member> members = new ArrayList<>(fineTeam.getMembers());
		System.out.println("size = " + members.size());
		System.out.println(members);
	}
}

@Repository
class TeamRepository{
	@PersistenceContext
	private EntityManager em;

	public Team save(Team team){
		em.persist(team);
		return team;
	}

	public Team fineOne(Long id){
		return em.find(Team.class, id);
	}
}
