package com.ch14.class01.step02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CollectionExample02 implements ApplicationRunner {

	@Autowired
	private ParentService parentService;

	@Autowired
	private CollectionChildService collectionChildService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Parent parent = new Parent();
		parent = parentService.save(parent);
		collectionChildService.save(CollectionChild.builder()
			.parent(parent)
			.build());
		collectionChildService.save(CollectionChild.builder()
			.parent(parent)
			.build());

		// 단순 추가만 한다. 결과는 항상 true이다.
		// 중복된 엔티티가 있는지 비교하지 않고 단순히 저장만 한다
		CollectionChild collectionChild = CollectionChild.builder()
			.parent(parent)
			.build();
		boolean result1 = parent.getCollection().add(collectionChild);
		log.info("result1 = {}", result1); // true

		boolean result2 = parent.getCollection().contains(collectionChild); // equals 비교
		log.info("result2 = {}", result2); // true

		boolean result3 = parent.getCollection().remove(collectionChild);// equals 비교
		log.info("result3 = {}", result3); // true
	}

	public static void main(String[] args) {
		SpringApplication.run(CollectionExample02.class, args);
	}
}
