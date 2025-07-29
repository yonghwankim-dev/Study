package com.ch13.class02.step03;

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
	private OrderFacade orderFacade;

	public String view(Long orderId){
		Order order = orderFacade.fineOrder(orderId);
		Member member = order.getMember();
		return member.getName();
	}
}

@Service
class OrderFacade{
	@Autowired
	private OrderService orderService;

	@Transactional
	public Order save(Order order) {
		return orderService.save(order);
	}

	@Transactional(readOnly = true)
	public Order fineOrder(Long id) {
		Order order = orderService.fineOrder(id);
		// Presentation 레이어가 필요한 프록시 객체를 강제로 초기화한다
		order.getMember().getName();
		return order;
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
		return orderRepository.findOrder(id);
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
