package com.ch13.class02.step02;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * @Transactional이 없으면 트랜잭션 범위가 없게 되므로 데이터베이스에서 가져오는 엔티티들은 준영속 상태가 된다
 * 준영속 상태에서 지연 로딩 시도시 예외가 발생한다
 *
 * 서비스 레이어에 @Transactional이 존재하고 스프링 OSIV가 활성화되어 있으면
 * 컨트롤러나 뷰 레이어까지도 엔티티는 영속 상태이기 때문에 지연 로딩이 가능하다.
 * 하지만, 데이터 수정시에는 반영되지 않는다. 왜냐하면 영속성 컨텍스트가 Presentation 레이어에서 종료되면
 * 플러시하지 않기 때문이다.
 */
@Slf4j
@SpringBootApplication
public class GlobalFetchStrategyExample02 implements ApplicationRunner {

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderController orderController;

	@Override
	public void run(ApplicationArguments args) {
		Member member = memberService.save(Member.builder()
			.name("홍길동")
			.build());
		log.info("member 초기화 완료, member={}", member);


		Order order = orderService.save(Order.builder()
			.member(member)
			.build());
		log.info("order 초기화 완료, order={}", order);

		String memberName1 = orderController.view(order.getId());
		log.info("memberName1 = {}", memberName1);
	}

	public static void main(String[] args) {
		SpringApplication.run(GlobalFetchStrategyExample02.class, args);
	}
}
