package com.myshop.order.infrastructure;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.myshop.order.query.dto.OrderView;
import com.myshop.order.domain.OrderViewDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class JpaOrderViewDao implements OrderViewDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<OrderView> selectByOrderer(String ordererId) {
		String selectQuery = "";
		TypedQuery<OrderView> query = em.createQuery(selectQuery, OrderView.class);
		return query.getResultList();
	}
}
