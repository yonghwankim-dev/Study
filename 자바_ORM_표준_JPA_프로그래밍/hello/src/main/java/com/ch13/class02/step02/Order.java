package com.ch13.class02.step02;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Table;

import org.hibernate.Hibernate;
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
		Member member = order.getMember();
		return member.getName();
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

	@Transactional(readOnly = true)
	public Order fineOrder(Long id){
		Order order = orderRepository.findOrder(id);
		log.info("order : {}", order);
		Member member = order.getMember(); // 초기화되지 않은 단순 프록시 객체 반환
		member.getName(); // 프록시 객체를 강제로 초기화한다

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
