package com.ch15.class06.step05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 배치 처리 : JPA 등록 배치시 처리 방법
 * 영속성 컨텍스트에 너무 많은 엔티티가 저장되면 메모리 부족 오류가 발생할 수 있기 때문에
 * 특정 건수마다 저장할 때마다 플러시를 호출 및 클리어해서 영속성 컨텍스트를 초기화하는 방향으로
 * 대량의 데이터를 배치 등록 처리합니다.
 */
@SpringBootApplication
public class JPARegisterBatchExample implements ApplicationRunner {

	@Autowired
	private ProductService productService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		productService.registerBatchProduct();
	}

	public static void main(String[] args) {
		SpringApplication.run(JPARegisterBatchExample.class, args);
	}
}
