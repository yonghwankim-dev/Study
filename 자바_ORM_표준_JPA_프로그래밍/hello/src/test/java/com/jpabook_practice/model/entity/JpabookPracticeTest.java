package com.jpabook_practice.model.entity;

import static com.jpabook_practice.model.entity.DeliveryStatus.*;
import static com.jpabook_practice.model.entity.OrderStatus.*;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jpabook_practice.model.entity.Address;
import com.jpabook_practice.model.entity.Category;
import com.jpabook_practice.model.entity.CategoryItem;
import com.jpabook_practice.model.entity.Delivery;
import com.jpabook_practice.model.entity.Member;
import com.jpabook_practice.model.entity.Order;
import com.jpabook_practice.model.entity.OrderItem;
import com.jpabook_practice.model.entity.item.Book;
import com.jpabook_practice.model.entity.item.Item;

class JpabookPracticeTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@BeforeAll
	static void beforeAll() {
		emf = Persistence.createEntityManagerFactory("jpabook_practice");
	}

	@BeforeEach
	public void beforeEach() {
		em = emf.createEntityManager();
	}

	@Test
	@DisplayName("엔티티 객체 초기화")
	public void test1() {
		// given
		Member member = new Member();
		Delivery delivery = new Delivery();
		Order order = new Order();
		OrderItem orderItem = new OrderItem();
		Item jpa = new Book();
		Category book = new Category();
		Category it = new Category();
		CategoryItem categoryItem = new CategoryItem();

		member.setAddress(new Address("서울", "강남", "29000"));

		delivery.setDeliveryStatus(READY);
		delivery.setAddress(new Address("서울", "강남", "29000"));

		order.setMember(member);
		order.setOrderStatus(ORDER);
		order.setOrderDate(new Date());
		order.setDelivery(delivery);
		order.addOrderItem(orderItem);

		orderItem.setOrder(order);
		orderItem.setItem(jpa);
		orderItem.setCount(2);
		orderItem.setOrderPrice(jpa.getPrice() * orderItem.getCount());

		book.addChildCategory(it);
		it.setParent(book);
		it.setName("IT");

		categoryItem.setCategory(it);
		categoryItem.setItem(jpa);

		jpa.setName("JPA");
		jpa.setPrice(10000);
		jpa.setStockQuantity(5);
		jpa.addCategoryItem(categoryItem);

		// when
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(member);
		em.persist(delivery);
		em.persist(order);
		em.persist(book);
		em.persist(it);
		em.persist(jpa);
		em.persist(categoryItem);
		em.persist(orderItem);

		em.flush();
		// then
		Member findMember = em.find(Member.class, member.getId());
		Order findOrder = findMember.getOrders().stream().findAny().orElseThrow();
		Delivery findDelivery = findOrder.getDelivery();
		OrderItem findOrderItem = findOrder.getOrderItems().stream().findAny().orElseThrow();
		Item findItem = findOrderItem.getItem();
		CategoryItem findCategoryItem = findItem.getCategoryItems().stream().findAny().orElseThrow();

		SoftAssertions assertions = new SoftAssertions();
		assertions.assertThat(findMember).isEqualTo(member);
		assertions.assertThat(findOrder).isEqualTo(order);
		assertions.assertThat(findDelivery).isEqualTo(delivery);
		assertions.assertThat(findOrderItem).isEqualTo(orderItem);
		assertions.assertThat(findItem).isEqualTo(jpa);
		assertions.assertThat(findCategoryItem).isEqualTo(categoryItem);
		assertions.assertThat(findCategoryItem.getCategory()).isEqualTo(it);

		transaction.rollback();
	}

	@Test
	@DisplayName("Order 엔티티가 주어지고 영속성 컨텍스트에 참조시 지연 로딩인 Member는 조인되지 않고 쿼리합니다.")
	public void lazy() {
		// given
		Member member = new Member();
		Delivery delivery = new Delivery();
		Order order = new Order();
		OrderItem orderItem = new OrderItem();
		Item jpa = new Book();
		Category book = new Category();
		Category it = new Category();
		CategoryItem categoryItem = new CategoryItem();

		member.setAddress(new Address("서울", "강남", "29000"));

		delivery.setDeliveryStatus(READY);
		delivery.setAddress(new Address("서울", "강남", "29000"));

		order.setMember(member);
		order.setOrderStatus(ORDER);
		order.setOrderDate(new Date());
		order.setDelivery(delivery);
		order.addOrderItem(orderItem);

		orderItem.setOrder(order);
		orderItem.setItem(jpa);
		orderItem.setCount(2);
		orderItem.setOrderPrice(jpa.getPrice() * orderItem.getCount());

		book.addChildCategory(it);
		it.setParent(book);
		it.setName("IT");

		categoryItem.setCategory(it);
		categoryItem.setItem(jpa);

		jpa.setName("JPA");
		jpa.setPrice(10000);
		jpa.setStockQuantity(5);
		jpa.addCategoryItem(categoryItem);

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(member);
		em.persist(delivery);
		em.persist(order);
		em.persist(book);
		em.persist(it);
		em.persist(jpa);
		em.persist(categoryItem);
		em.persist(orderItem);
		em.flush();
		em.clear();
		// when
		/**
		 * select
		 *         order0_.ORDER_ID as order_id1_6_0_,
		 *         order0_.createdDate as createdd2_6_0_,
		 *         order0_.lastModifiedDate as lastmodi3_6_0_,
		 *         order0_.DELIVERY_ID as delivery6_6_0_,
		 *         order0_.MEMBER_ID as member_i7_6_0_,
		 *         order0_.orderDate as orderdat4_6_0_,
		 *         order0_.orderStatus as ordersta5_6_0_
		 *     from
		 *         ORDERS order0_
		 *     where
		 *         order0_.ORDER_ID=?
		 */
		Order findOrder = em.find(Order.class, order.getId());
		OrderItem newOrderItem = new OrderItem();
		newOrderItem.setCount(2);
		newOrderItem.setOrderPrice(2000);
		newOrderItem.setOrder(order);
		newOrderItem.setItem(jpa);
		findOrder.addOrderItem(newOrderItem);
		em.flush();

		OrderItem findOrderItem = em.find(OrderItem.class, newOrderItem.getId());
		// then
		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(findOrder).isNotNull();
			softAssertions.assertThat(findOrder.getOrderItems()).contains(findOrderItem);
		});
		transaction.rollback();
	}
}
