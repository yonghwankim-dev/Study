package jpabook.jpashop.repository.jpa.order;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import com.querydsl.jpa.JPQLQuery;

import jpabook.jpashop.domain.member.QMember;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order.QOrder;
import jpabook.jpashop.service.persistence.order.OrderSearch;

public class CustomOrderRepositoryImpl extends QuerydslRepositorySupport implements CustomOrderRepository {

	public CustomOrderRepositoryImpl() {
		super(Order.class);
	}

	@Override
	public List<Order> search(OrderSearch orderSearch) {
		QOrder order = QOrder.order;
		QMember member = QMember.member;

		JPQLQuery<Order> query = from(order);

		if (StringUtils.hasText(orderSearch.getMemberName())) {
			query.leftJoin(order.member, member)
				.where(member.name.contains(orderSearch.getMemberName()));
		}

		if (orderSearch.getOrderStatus() != null) {
			query.where(order.status.eq(orderSearch.getOrderStatus()));
		}
		return query.fetch();
	}
}
