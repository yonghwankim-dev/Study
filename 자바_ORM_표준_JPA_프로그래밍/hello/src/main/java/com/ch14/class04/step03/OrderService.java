package com.ch14.class04.step03;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Subgraph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Transactional
	public Order order(Order order){
		return orderRepository.save(order);
	}

	@Transactional(readOnly = true)
	public Order findOrderWithMember(Long id) {
		return orderRepository.findOrderWithMember(id);
	}

	@Transactional
	public Order findOrderWithAll(Long id){
		return orderRepository.findOrderWithAll(id);
	}

	@Transactional
	public List<Order> findOrderWithAllUsingJPQL(Long id){
		return orderRepository.fineOrdersWithAllUsingJPQL(id);
	}

	@Transactional
	public List<Order> findOrderWithAllUsingJPQLWithInnerJoin(Long id) {
		return orderRepository.findOrderWithAllUsingJPQLWithInnerJoin(id);
	}

	@Transactional
	public Order findOrderWithMemberUsingDynamicEntityGraph(Long id){
		return orderRepository.findOrderWithMemberUsingDynamicEntityGraph(id);
	}

	@Transactional
	public Order findOrderWithMemberAndItemUsingDynamicEntityGraphAndSubGraph(Long id){
		return orderRepository.findOrderWithMemberAndItemUsingDynamicEntityGraphAndSubGraph(id);
	}
}

@Repository
class OrderRepository{
	@PersistenceContext
	private EntityManager em;

	public Order save(Order order){
		em.persist(order);
		return order;
	}

	public Order findOrderWithMember(Long id) {
		EntityGraph<?> graph = em.getEntityGraph("Order.withMember");
		Map<String, Object> hints = new HashMap<>();
		hints.put("javax.persistence.fetchgraph", graph);
		return em.find(Order.class, id, hints);
	}

	public Order findOrderWithAll(Long id){
		EntityGraph<?> graph = em.getEntityGraph("Order.withAll");
		Map<String, Object> hints = new HashMap<>();
		hints.put("javax.persistence.fetchgraph", graph);
		return em.find(Order.class, id, hints);
	}

	public List<Order> fineOrdersWithAllUsingJPQL(Long id){
		return em.createQuery("select o from Order o where o.id = :orderId", Order.class)
			.setParameter("orderId", id)
			.setHint("javax.persistence.fetchgraph", em.getEntityGraph("Order.withAll"))
			.getResultList();
	}

	public List<Order> findOrderWithAllUsingJPQLWithInnerJoin(Long id) {
		return em.createQuery("select o from Order o join fetch o.member where o.id = :orderId", Order.class)
			.setParameter("orderId", id)
			.setHint("javax.persistence.fetchgraph", em.getEntityGraph("Order.withAll"))
			.getResultList();
	}

	public Order findOrderWithMemberUsingDynamicEntityGraph(Long id){
		EntityGraph<Order> entityGraph = em.createEntityGraph(Order.class);
		entityGraph.addAttributeNodes("member");

		Map<String, Object> hints = new HashMap<>();
		hints.put("javax.persistence.fetchgraph", entityGraph);

		return em.find(Order.class, id, hints);
	}

	public Order findOrderWithMemberAndItemUsingDynamicEntityGraphAndSubGraph(Long id){
		EntityGraph<Order> graph = em.createEntityGraph(Order.class); // 동적 엔티티 그래프 생성
		graph.addAttributeNodes("member"); // 그래프에 member 속성 포함
		Subgraph<Object> orderItems = graph.addSubgraph("orderItems"); // 서브 그래프 생성
		orderItems.addAttributeNodes("item"); // 서브 그래프가 item 속성을 포함하도록 함

		Map<String, Object> hints = new HashMap<>();
		hints.put("javax.persistence.fetchgraph", graph);

		return em.find(Order.class, id, hints);
	}
}
