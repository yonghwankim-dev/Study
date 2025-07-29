package jpabook.jpashop.controller.item;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.persistence.item.PersistenceItemService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemRestController {

	private final PersistenceItemService persistenceItemService;

	@PostMapping("/new")
	public String create(@RequestBody Book item) {
		Long itemId = persistenceItemService.saveItem(item);
		return "new Item Id : " + itemId;
	}

	@GetMapping
	public List<Item> list() {
		return persistenceItemService.findItems();
	}

	@PostMapping("/{itemId}/edit")
	public Item updateItem(@RequestBody Book item) {
		Long itemId = persistenceItemService.saveItem(item);
		return persistenceItemService.fineOne(itemId);
	}
}
