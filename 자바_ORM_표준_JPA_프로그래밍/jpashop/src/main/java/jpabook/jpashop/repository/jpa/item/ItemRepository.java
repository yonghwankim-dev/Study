package jpabook.jpashop.repository.jpa.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import jpabook.jpashop.domain.item.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

	@Modifying
	@Query("update Item i set i.price = i.price * 1.1 where i.stockQuantity < :stockQuantity")
	int bulkPriceUp(@Param("stockQuantity") Integer stockQuantity);
}
