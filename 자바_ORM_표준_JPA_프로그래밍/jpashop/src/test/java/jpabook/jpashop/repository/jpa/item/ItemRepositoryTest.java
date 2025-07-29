package jpabook.jpashop.repository.jpa.item;

import java.util.Iterator;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.QItem;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

	@Autowired
	private ItemRepository itemRepository;

	@DisplayName("QueryDSL을 이용하여 상품 조회")
	@Test
	void findAll() {
		// given
		itemRepository.save(createBook());
		QItem item = QItem.item;

		// when
		Iterator<Item> iterator = itemRepository.findAll(
			item.name.contains("JPA").and(item.price.between(10000, 20000))
		).iterator();

		// then
		List<Item> items = Lists.newArrayList(iterator);
		Assertions.assertThat(items).hasSize(1);
	}

	private Book createBook() {
		return Book.builder()
			.name("JPA")
			.price(15000)
			.stockQuantity(10)
			.build();
	}

}
