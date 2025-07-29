package com.ch13.class02.step0;

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

	public String view(Long orderId){
		Order order = orderService.findOne(orderId);
		Member member = order.getMember();
		return member.getName(); // 지연 로딩시 예외 발생
	}
}

@Service
class OrderService{

	@Autowired
	private OrderRepository orderRepository;

	@Transactional(readOnly = true)
	public Order findOne(Long orderId) {
		return orderRepository.findOne(orderId);
	}

	@Transactional
	public Order save(Order order){
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

	public Order save(Order order){
		em.persist(order);
		return order;
	}
}
