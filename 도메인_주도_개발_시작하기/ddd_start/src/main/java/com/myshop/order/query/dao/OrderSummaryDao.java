package com.myshop.order.query.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.myshop.order.query.dto.OrderSummary;
import com.myshop.order.query.dto.OrderView;

public interface OrderSummaryDao extends Repository<OrderSummary, String> {
	void save(OrderSummary orderSummary);

	OrderSummary findByNumber(String number);

	List<OrderSummary> findAll(Specification<OrderSummary> spec);

	List<OrderSummary> findAll(Specification<OrderSummary> spec, Pageable pageable);

	List<OrderSummary> findByOrdererIdOrderByNumberDesc(String ordererId);

	List<OrderSummary> findByOrdererIdOrderByOrderDateDescNumberAsc(String ordererId);

	List<OrderSummary> findByOrdererId(String ordererId, Sort sort);

	@Query("""
			select new com.myshop.order.query.dto.OrderView(
				o.orderNo, o.state, m.name, m.id, p.productInfo.productName
			)
			from Order o join o.orderLines ol, Member m, Product p
			where o.orderer.memberId.id = :ordererId
			and o.orderer.memberId.id = m.id.id
			and index(ol) = 0
			and ol.productId.id = p.id.id
			order by o.orderNo.id desc
		""")
	List<OrderView> findOrderView(@Param("ordererId") String ordererId);

	void deleteAll();
}
