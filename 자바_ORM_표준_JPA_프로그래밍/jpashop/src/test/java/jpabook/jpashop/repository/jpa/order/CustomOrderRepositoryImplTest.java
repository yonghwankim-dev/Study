package jpabook.jpashop.repository.jpa.order;

import java.util.List;

import org.assertj.core.api.Assertions;
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
import jpabook.jpashop.repository.jpa.item.ItemRepository;
import jpabook.jpashop.repository.jpa.member.MemberRepository;
import jpabook.jpashop.service.persistence.order.OrderSearch;
import jpabook.jpashop.service.persistence.order.PersistenceOrderService;

@SpringBootTest
@Transactional
class CustomOrderRepositoryImplTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private PersistenceOrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@DisplayName("사용자가 주문중인 주문을 검색한다")
	@Test
	void search() {
		// given
		Member member = createMember("김용환");
		memberRepository.save(member);

		Item item = createBook();
		itemRepository.save(item);
		int orderCount = 2;
		orderService.order(member.getId(), item.getId(), orderCount);

		// when
		List<Order> orders = orderRepository.search(new OrderSearch("김용환", OrderStatus.ORDER));

		// then
		Assertions.assertThat(orders).hasSize(1);
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
