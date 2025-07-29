package com.ch15.class06.step07;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 배치 처리 : JPA 수정 배치시 처리 방법 (커서)
 * 첫번째 방법 : 페이징 처리, 데이터베이스의 페이징 기능 사용
 * 두번째 방법 : 커서, 데이터베이스가 지원하는 커서 기능을 사용
 *
 * JPA는 JDBC 커서를 지원하지 않기 때문에 하이버네이트 세션을 사용해야 합니다
 */
@SpringBootApplication
public class JPABatchUpdateExample implements ApplicationRunner {

	@Autowired
	private ProductService productService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		productService.updateBatchProduct();
	}

	public static void main(String[] args) {
		SpringApplication.run(JPABatchUpdateExample.class, args);
	}
}
