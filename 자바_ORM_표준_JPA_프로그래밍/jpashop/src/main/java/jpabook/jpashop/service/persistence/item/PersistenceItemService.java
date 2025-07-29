package jpabook.jpashop.service.persistence.item;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.persistence.item.PersistenceItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PersistenceItemService {

	private final PersistenceItemRepository persistenceItemRepository;

	public Long saveItem(Item item) {
		persistenceItemRepository.save(item);
		return item.getId();
	}

	public List<Item> findItems() {
		return persistenceItemRepository.findAll();
	}

	public Item fineOne(Long itemId) {
		return persistenceItemRepository.fineOne(itemId);
	}
}
