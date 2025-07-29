package jpabook.jpashop.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.OrderStatus;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.persistence.item.PersistenceItemRepository;
import jpabook.jpashop.repository.persistence.member.PersistenceMemberRepository;
import jpabook.jpashop.repository.persistence.order.PersistenceOrderRepository;
import jpabook.jpashop.service.persistence.order.PersistenceOrderService;

@SpringBootTest
@Transactional
class PersistenceOrderServiceTest {

	@Autowired
	private PersistenceOrderService persistenceOrderService;

	@Autowired
	private PersistenceMemberRepository persistenceMemberRepository;

	@Autowired
	private PersistenceItemRepository persistenceItemRepository;

	@Autowired
	private PersistenceOrderRepository persistenceOrderRepository;

	@DisplayName("사용자는 주문을 한다")
	@Test
	void order() {
		// given
		Member member = createMember("김용환");
		persistenceMemberRepository.save(member);

		Item item = createBook();
		persistenceItemRepository.save(item);

		int orderCount = 2;
		// when
		Long orderId = persistenceOrderService.order(member.getId(), item.getId(), orderCount);

		// then
		Order findOrder = persistenceOrderRepository.fineOne(orderId);
		assertAll(
			() -> assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER),
			() -> assertThat(findOrder.getOrderItems()).hasSize(1),
			() -> assertThat(findOrder.getTotalPrice()).isEqualTo(20000),
			() -> assertThat(item.getStockQuantity()).isEqualTo(8)
		);
	}

	@DisplayName("사용자는 상품 주문시 재고 수량보다 많은 개수로 주문할 수 없다")
	@Test
	void orderWithOverStockQuantity() {
		// given
		Member member = createMember("김용환");
		persistenceMemberRepository.save(member);

		Item item = createBook();
		persistenceItemRepository.save(item);

		int orderCount = 11;
		// when
		Throwable throwable = catchThrowable(
			() -> persistenceOrderService.order(member.getId(), item.getId(), orderCount));

		// then
		assertThat(throwable)
			.isInstanceOf(NotEnoughStockException.class)
			.hasMessage("need more stock");
	}

	@DisplayName("사용자는 주문한 상품을 취소한다")
	@Test
	void cancel() {
		// given
		Member member = createMember("김용환");
		persistenceMemberRepository.save(member);

		Item item = createBook();
		persistenceItemRepository.save(item);
		int orderCount = 2;
		Long orderId = persistenceOrderService.order(member.getId(), item.getId(), orderCount);
		// when
		persistenceOrderService.cancelOrder(orderId);

		// then
		Order findOrder = persistenceOrderRepository.fineOne(orderId);
		assertAll(
			() -> assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL),
			() -> assertThat(item.getStockQuantity()).isEqualTo(10)
		);
	}

	private Book createBook() {
		return Book.builder()
			.name("JPA")
			.price(10000)
			.stockQuantity(10)
			.build();
	}

	private Member createMember(String name) {
		return Member.builder()
			.name(name)
			.address(createAddress())
			.build();
	}

	private Address createAddress() {
		return Address.builder()
			.city("서울")
			.street("강남")
			.zipcode("12345")
			.build();
	}
}
