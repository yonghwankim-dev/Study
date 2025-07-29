package jpabook.jpashop.service.jpa.item;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.jpa.item.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	public int bulkPriceUp(int stockQuantity) {
		return itemRepository.bulkPriceUp(stockQuantity);
	}

	public Item fineOne(Long id) {
		return itemRepository.findById(id).orElseThrow();
	}
}
