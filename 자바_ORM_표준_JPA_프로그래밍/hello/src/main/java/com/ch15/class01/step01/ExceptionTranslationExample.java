package com.ch15.class01.step01;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.EmptyResultDataAccessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ExceptionTranslationExample implements ApplicationRunner {

	@Autowired
	private NoResultExceptionTestRepository repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try{
			Member member = repository.findMember();
			System.out.println(member);
		}catch (EmptyResultDataAccessException exception){
			log.error(exception.getMessage(), exception);
		}

		try{
			Member member1 = repository.fineMember2();
		}catch (NoResultException exception){
			log.error(exception.getMessage(), exception);
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(ExceptionTranslationExample.class, args);
	}
}
