package com.ch13.class02.step01;

import java.util.List;
import java.util.stream.Collectors;

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

	@ManyToOne(fetch = FetchType.EAGER) // 즉시 로딩 전략
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
		return member.getName(); // 이미 로딩된 엔티티
	}

	public List<String> viewAll(){
		List<Order> orders = orderService.findAll();
		return orders.stream()
			.map(Order::getMember)
			.map(Member::getName)
			.collect(Collectors.toList());
	}

	public List<String> viewAllUsingFetchJoin(){
		List<Order> orders = orderService.findAllUsingFetchJoin();
		return orders.stream()
			.map(Order::getMember)
			.map(Member::getName)
			.collect(Collectors.toList());
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

	@Transactional(readOnly = true)
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<Order> findAllUsingFetchJoin(){
		return orderRepository.findAllUsingFetchJoin();
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

	public List<Order> findAll() {
		return em.createQuery("select o from Order o", Order.class)
			.getResultList();
	}

	// 페치 조인을 사용
	public List<Order> findAllUsingFetchJoin(){
		return em.createQuery("select o from Order o join fetch o.member", Order.class)
			.getResultList();
	}
}
