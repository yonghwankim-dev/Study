package com.ch15.class06.step08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 배치 처리 : JPA 수정 배치시 처리 방법 (하이버네이트 무상태 세션 사용)
 * 영속성 컨텍스트를 만들지 않고 2차 캐시도 사용하지 않는 것을 무상태 세션이라고 한다
 * 엔티티를 수정하기 위해서는 무상태 세션이 제공하는 update() 메소드를 직접 호출하여 수정한다
 */
@SpringBootApplication
public class HibernateStateLessSessionExample implements ApplicationRunner {

	@Autowired
	private ProductService productService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		productService.updateBatchProduct();
	}

	public static void main(String[] args) {
		SpringApplication.run(HibernateStateLessSessionExample.class, args);
	}
}
