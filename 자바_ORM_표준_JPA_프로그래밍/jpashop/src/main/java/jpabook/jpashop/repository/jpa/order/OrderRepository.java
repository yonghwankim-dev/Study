package jpabook.jpashop.repository.jpa.order;

import org.springframework.data.jpa.repository.JpaRepository;

import jpabook.jpashop.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {
}
