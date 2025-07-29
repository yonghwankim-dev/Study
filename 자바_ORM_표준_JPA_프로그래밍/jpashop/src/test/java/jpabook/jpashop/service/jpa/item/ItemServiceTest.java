package jpabook.jpashop.service.jpa.item;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.jpa.item.ItemRepository;

@SpringBootTest
@Transactional
class ItemServiceTest {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemRepository itemRepository;

	@DisplayName("stockQuantity 보다 작은 재고 상품에 대해서 10% 가격을 올린다")
	@Test
	void bulkPriceUp() {
		// given
		Book book = itemRepository.save(createBook());
		// when
		int update = itemService.bulkPriceUp(100);
		// then
		assertThat(update).isGreaterThan(0);
	}

	private Book createBook() {
		return Book.builder()
			.name("JPA")
			.price(10000)
			.stockQuantity(10)
			.build();
	}

}
