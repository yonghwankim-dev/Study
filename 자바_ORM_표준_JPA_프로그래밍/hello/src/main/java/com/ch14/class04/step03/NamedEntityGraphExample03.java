package com.ch14.class04.step03;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jpabook_practice.model.entity.DeliveryStatus;

@SpringBootApplication
public class NamedEntityGraphExample03 implements ApplicationRunner {

	@Autowired
	private OrderService orderService;

	@Autowired
	private MemberService memberService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Member member = Member.builder()
			.name("김용환")
			.address(Address.builder()
				.city("서울")
				.street("강남구")
				.zipcode("123456")
				.build())
			.build();
		member = memberService.save(member);

		Delivery delivery = Delivery.builder()
			.address(Address.builder()
				.city("서울")
				.street("강남구")
				.zipcode("123456")
				.build())
			.status(DeliveryStatus.READY)
			.build();

		Order order = Order.builder()
			.member(member)
			.delivery(delivery)
			.orderDate(LocalDateTime.now())
			.orderStatus(OrderStatus.ORDER)
			.build();
		member.getOrders().add(order);
		Order saveOrder = orderService.order(order);

		List<Order> orders = orderService.findOrderWithAllUsingJPQL(saveOrder.getId());
		System.out.println(orders);

		List<Order> orders2 = orderService.findOrderWithAllUsingJPQLWithInnerJoin(saveOrder.getId());
		System.out.println(orders2);

		Order findOrder = orderService.findOrderWithMemberUsingDynamicEntityGraph(
			saveOrder.getId());
		System.out.println(findOrder);

		Order findOrder2 = orderService.findOrderWithMemberAndItemUsingDynamicEntityGraphAndSubGraph(
			saveOrder.getId());
		System.out.println(findOrder2);
	}

	public static void main(String[] args) {
		SpringApplication.run(NamedEntityGraphExample03.class);
	}
}
