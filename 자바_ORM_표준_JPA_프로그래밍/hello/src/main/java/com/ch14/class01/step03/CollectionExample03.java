package com.ch14.class01.step03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CollectionExample03 implements ApplicationRunner {

	@Autowired
	private ParentController parentController;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Parent parent = new Parent();
		parent = parentController.save(parent);
		SetChild setChild = new SetChild();

		boolean result1 = parentController.addSetChild(parent.getId(), setChild);
		log.info("setChild 추가 결과 : {}", result1);

		boolean result2 = parentController.containsSetChild(parent.getId(), setChild);
		log.info("setChild 포함 결과 : {}", result2);

		parentController.removeSetChild(parent.getId(), setChild);
		log.info("setChild 제거 결과 : {}", setChild);
	}

	public static void main(String[] args) {
		SpringApplication.run(CollectionExample03.class, args);
	}
}
