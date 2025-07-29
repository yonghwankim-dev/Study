package com.ch13.class03.other;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "orders")
@Getter
@ToString(exclude = {"member"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY) // 즉시 로딩 전략
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

	public String view(Long orderId){
		Order order = orderService.fineOrder(orderId);
		return order.getMember().getName();
	}
}

@Slf4j
@Service
class OrderService{

	@Autowired
	private OrderRepository orderRepository;

	@Transactional
	public Order save(Order order){
		return orderRepository.save(order);
	}

	public Order fineOrder(Long id){
		Order order = orderRepository.findOrder(id);
		String name = order.getMember().getName(); // 준영속 상태에서 member 엔티티 지연 로딩 시도 -> error
		log.info("name : {}", name);
		return order;
	}
}

@Repository
class OrderRepository{
	@PersistenceContext
	private EntityManager em;

	public Order save(Order order) {
		em.persist(order);
		return order;
	}

	public Order findOrder(Long id) {
		return em.find(Order.class, id);
	}
}
