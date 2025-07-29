package jpabook.jpashop.repository.jpa.order;

import java.util.List;

import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.service.persistence.order.OrderSearch;

public interface CustomOrderRepository {
	List<Order> search(OrderSearch orderSearch);
}
