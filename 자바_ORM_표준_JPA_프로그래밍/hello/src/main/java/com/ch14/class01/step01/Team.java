package com.ch14.class01.step01;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany
	@JoinColumn
	private Collection<Member> members = new ArrayList<>();

	@OneToMany
	List<Member> list = new ArrayList<>();

	@OneToMany
	Set<Member> set = new HashSet<>();

	@OneToMany
	@OrderColumn
	List<Member> orderColumnList = new ArrayList<>();
}

@Controller
class TeamController{
	@Autowired
	private TeamService teamService;

	public String save(Team team){
		return teamService.save(team).toString();
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
}

@Repository
class TeamRepository{
	@PersistenceContext
	private EntityManager em;

	public Team save(Team team){
		em.persist(team);
		return team;
	}
}

