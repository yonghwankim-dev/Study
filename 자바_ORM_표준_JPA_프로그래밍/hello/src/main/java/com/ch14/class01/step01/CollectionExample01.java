package com.ch14.class01.step01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CollectionExample01 implements ApplicationRunner {

	@Autowired
	private TeamService teamService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Team team = new Team();
		log.info("before persist = {}", team.getMembers().getClass());
		log.info("before persist = {}", team.getList().getClass());
		log.info("before persist = {}", team.getSet().getClass());
		log.info("before persist = {}", team.getOrderColumnList().getClass());
		Team savedTeam = teamService.save(team);
		log.info("after persist = {}", savedTeam.getMembers().getClass());
		log.info("after persist = {}", savedTeam.getList().getClass());
		log.info("after persist = {}", team.getSet().getClass());
		log.info("after persist = {}", team.getOrderColumnList().getClass());
	}

	public static void main(String[] args) {
		SpringApplication.run(CollectionExample01.class, args);
	}
}
