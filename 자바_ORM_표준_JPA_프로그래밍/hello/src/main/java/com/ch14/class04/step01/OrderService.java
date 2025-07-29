package com.ch14.class04.step01;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
