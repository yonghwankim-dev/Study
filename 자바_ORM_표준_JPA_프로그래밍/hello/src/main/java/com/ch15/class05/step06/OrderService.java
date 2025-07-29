package com.ch15.class05.step06;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
	@PersistenceContext
	private EntityManager em;

	public Long save(Order order){
		em.persist(order);
		return order.getId();
	}
}
