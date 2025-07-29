package com.ch14.class01.step05;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class OrderByExample01 implements ApplicationRunner {

	@Autowired
	private MemberService memberService;

	@Autowired
	private TeamService teamService;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Team team = new Team("팀A");
		teamService.save(team);

		Member member1 = new Member("김용환");
		member1.setTeam(team);

		Member member2 = new Member("홍길동");
		member2.setTeam(team);

		Member member3 = new Member("김가가");
		member3.setTeam(team);
		memberService.save(member1);
		memberService.save(member2);
		memberService.save(member3);

		teamService.printMembers(team.getId());
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderByExample01.class, args);
	}
}
