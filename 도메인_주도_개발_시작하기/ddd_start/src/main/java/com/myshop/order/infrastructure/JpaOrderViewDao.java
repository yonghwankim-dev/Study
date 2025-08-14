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
		String selectQuery = """
			select new com.myshop.order.query.dto.OrderView(o, m, p)
			from Order o join o.orderLines ol, Member m, Product p
			where o.orderer.memberId.id = :ordererId
			and o.orderer.memberId = m.id
			and index(ol) = 0
			and ol.productId = p.id
			order by o.id.id desc
		""";
		TypedQuery<OrderView> query = em.createQuery(selectQuery, OrderView.class);
		query.setParameter("ordererId", ordererId);
		return query.getResultList();
	}
}
