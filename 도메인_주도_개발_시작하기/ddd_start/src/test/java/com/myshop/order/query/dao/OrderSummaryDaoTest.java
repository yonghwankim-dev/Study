package com.myshop.order.query.dao;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.FixedDomainFactory;
import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;
import com.myshop.order.domain.Address;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;
import com.myshop.order.domain.OrderState;
import com.myshop.order.domain.Receiver;
import com.myshop.order.domain.ShippingInfo;
import com.myshop.order.query.dto.OrderSummary;
import com.myshop.order.query.dto.OrderView;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderSummaryDaoTest {

	@Autowired
	private OrderSummaryDao orderSummaryDao;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	private String ordererId;

	private void saveMember() {
		Member member = FixedDomainFactory.createMember("12345");
		memberRepository.save(member);
	}

	private void saveProduct() {
		Product product = FixedDomainFactory.createProduct();
		productRepository.save(product);
	}

	private void saveOrders() {
		for (int i = 1; i <= 20; i++) {
			String orderId = String.format("1234567890%02d", i);
			Order order = FixedDomainFactory.createOrder(orderId);
			orderRepository.save(order);
		}
	}

	@AfterEach
	void tearDown() {
		memberRepository.deleteAll();
		productRepository.deleteAll();
		orderRepository.deleteAll();
	}

	@BeforeEach
	void setUp() {
		ordererId = "12345";
		saveMember();
		saveProduct();
		saveOrders();
	}

	@Test
	void shouldReturnOrderSummary() {
		Specification<OrderSummary> spec = OrderSummarySpecs.ordererId(ordererId);

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec);

		Assertions.assertThat(orderSummaries).isNotNull();
	}

	@Test
	void shouldReturnOrderSummaryByOrdererIdAndDateRange() {
		LocalDateTime now = LocalDateTime.now();
		Specification<OrderSummary> spec1 = OrderSummarySpecs.ordererId(ordererId);
		Specification<OrderSummary> spec2 = OrderSummarySpecs.orderDateBetween(
			now.minusMonths(1),
			now.plusMonths(1)
		);

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec1.and(spec2));

		Assertions.assertThat(orderSummaries).hasSize(20);
	}

	@Test
	void shouldReturnEmptyOrderSummary_whenNoMatchingOrdererId() {
		Specification<OrderSummary> spec = Specification.not(OrderSummarySpecs.ordererId(ordererId));

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec);

		Assertions.assertThat(orderSummaries).isEmpty();
	}

	@Test
	void shouldReturnOrderSummary_whenSpecIsNullable() {
		Specification<OrderSummary> nullableSpec = createNullableSpec();
		Specification<OrderSummary> otherSpec = OrderSummarySpecs.ordererId(ordererId);
		Specification<OrderSummary> spec = Specification.where(nullableSpec).and(otherSpec);

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec);

		Assertions.assertThat(orderSummaries).hasSize(20);
	}

	private Specification<OrderSummary> createNullableSpec() {
		return null;
	}

	@Test
	void shouldReturnOrderSummaryByOrdererId() {
		List<OrderSummary> orderSummaries = orderSummaryDao.findByOrdererIdOrderByNumberDesc("12345");

		Assertions.assertThat(orderSummaries).hasSize(20);
		Comparator<OrderSummary> comparator = Comparator.comparing(OrderSummary::getNumber).reversed();
		Assertions.assertThat(orderSummaries)
			.isSortedAccordingTo(comparator);
	}

	@Test
	void shouldReturnOrderSummaryByOrdererIdAndOrderDate() {
		List<OrderSummary> orderSummaries = orderSummaryDao.findByOrdererIdOrderByOrderDateDescNumberAsc(ordererId);

		Assertions.assertThat(orderSummaries).hasSize(20);
		Comparator<OrderSummary> comparator = Comparator.comparing(OrderSummary::getOrderDate,
				Comparator.reverseOrder())
			.thenComparing(OrderSummary::getNumber);
		Assertions.assertThat(orderSummaries)
			.isSortedAccordingTo(comparator);
	}

	@Test
	void shouldReturnOrderList_whenPassSpecification() {
		Specification<OrderSummary> spec = new OrdererIdSpec(ordererId);

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec);

		Assertions.assertThat(orderSummaries).hasSize(20);
	}

	@Test
	void shouldReturnOrderSummaryListByOrdererIdAndSort() {
		Sort sort = Sort.by("number").ascending();

		List<OrderSummary> orderSummaries = orderSummaryDao.findByOrdererId(ordererId, sort);

		Assertions.assertThat(orderSummaries)
			.hasSize(20)
			.isSortedAccordingTo(Comparator.comparing(OrderSummary::getNumber));
	}

	@Test
	void shouldReturnOrderSummaryByOrdererIdAndSortedNumberAscAndOrderDateDesc() {
		Sort sort1 = Sort.by("number").ascending();
		Sort sort2 = Sort.by("orderDate").descending();
		Sort sort = sort1.and(sort2);

		List<OrderSummary> orderSummaries = orderSummaryDao.findByOrdererId(ordererId, sort);

		Comparator<OrderSummary> comparator = Comparator
			.comparing(OrderSummary::getNumber)
			.thenComparing(OrderSummary::getOrderDate, Comparator.reverseOrder());
		Assertions.assertThat(orderSummaries)
			.hasSize(20)
			.isSortedAccordingTo(comparator);
	}

	@Test
	void shouldReturnOrderView() {
		List<OrderView> orderViews = orderSummaryDao.findOrderView(ordererId);

		Assertions.assertThat(orderViews).hasSize(20);
		Assertions.assertThat(orderViews.get(0).getNumber()).isEqualTo("123456789020");
		Assertions.assertThat(orderViews.get(0).getState()).isEqualTo(OrderState.PAYMENT_WAITING);
		Assertions.assertThat(orderViews.get(0).getMemberName()).isEqualTo("홍길동");
		Assertions.assertThat(orderViews.get(0).getMemberId()).isEqualTo("12345");
		Assertions.assertThat(orderViews.get(0).getProductName()).isEqualTo("Java Book");
	}

	@Transactional
	@Test
	void shouldChangedShippingInfo_whenFindOrderSummary() {
		String number = "123456789020";
		Order order = orderRepository.findById(new OrderNo(number)).orElseThrow();
		ShippingInfo newShippingInfo = new ShippingInfo(new Receiver("강감찬", "010-1234-5678"),
			"message", new Address("서울 강남구 역삼동", "735-17", "06235"));

		order.changeShippingInfo(newShippingInfo);

		Sort sort = Sort.by("number").descending();
		List<OrderSummary> orderSummaries = orderSummaryDao.findByOrdererId(order.getOrderer().getMemberId().getId(),
			sort);
		Assertions.assertThat(orderSummaries.get(0).getNumber()).isEqualTo(number);
		Assertions.assertThat(orderSummaries.get(0).getReceiverName()).isEqualTo("강감찬");
	}

	@Test
	void shouldReturnOrderSummaryListBySpecAndPageable() {
		Specification<OrderSummary> spec = OrderSummarySpecs.orderDateBetween(
			LocalDateTime.now().minusMonths(1),
			LocalDateTime.now().plusMonths(1)
		);
		Pageable pageable = PageRequest.of(1, 10);

		List<OrderSummary> orderSummaries = orderSummaryDao.findAll(spec, pageable);
		
		Assertions.assertThat(orderSummaries).hasSize(10);
	}
}
