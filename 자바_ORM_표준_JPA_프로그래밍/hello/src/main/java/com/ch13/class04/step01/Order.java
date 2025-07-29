package com.ch13.class04.step01;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "orders")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 전략
	private Member member;

	@Builder
	public Order(Long id, Member member) {
		this.id = id;
		this.member = member;
	}
}

@Controller
class OrderController{

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Transactional
	public String orderRequest(Order order) {
		long id = orderService.order(order);
		Order orderResult = orderRepository.findOne(id);
		return orderResult.toString();
	}
}

@Service
class OrderService{

	@Autowired
	private OrderRepository orderRepository;

	@Transactional
	public long order(Order order) {
		return orderRepository.save(order);
	}
}

@Repository
class OrderRepository{
	@PersistenceContext
	private EntityManager em;

	public Order findOne(Long orderId) {
		return em.find(Order.class, orderId);
	}

	public Long save(Order order){
		em.persist(order);
		return order.getId();
	}
}
