package jpabook.jpashop.service.persistence.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.delivery.Delivery;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.member.Member;
import jpabook.jpashop.domain.order.Order;
import jpabook.jpashop.domain.order_item.OrderItem;
import jpabook.jpashop.repository.persistence.item.PersistenceItemRepository;
import jpabook.jpashop.repository.persistence.member.PersistenceMemberRepository;
import jpabook.jpashop.repository.persistence.order.PersistenceOrderRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PersistenceOrderService {

	private final PersistenceOrderRepository persistenceOrderRepository;
	private final PersistenceMemberRepository persistenceMemberRepository;
	private final PersistenceItemRepository persistenceItemRepository;

	public Long order(Long memberId, Long itemId, int count) {
		// 엔티티 조회
		Member member = persistenceMemberRepository.fineOne(memberId);
		// 배송정보 생성
		Delivery delivery = Delivery.ready(member.getAddress());
		// 주문상품 생성
		Item item = persistenceItemRepository.fineOne(itemId);
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
		// 주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);
		// 주문 저장
		return persistenceOrderRepository.save(order);
	}

	public void cancelOrder(Long orderId) {
		// 주문 엔티티 조회
		Order order = persistenceOrderRepository.fineOne(orderId);
		// 주문 취소
		order.cancel();
	}

	public List<Order> fineOrders(OrderSearch orderSearch) {
		return persistenceOrderRepository.findAll(orderSearch);
	}

	public Order fineOne(Long orderId) {
		return persistenceOrderRepository.fineOne(orderId);
	}
}
