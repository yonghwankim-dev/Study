package com.myshop.order.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myshop.catalog.domain.product.Product;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.catalog.domain.product.ProductRepository;
import com.myshop.common.model.Money;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberRepository;
import com.myshop.order.ValidationError;
import com.myshop.order.ValidationErrorException;
import com.myshop.order.domain.Order;
import com.myshop.order.domain.OrderLine;
import com.myshop.order.domain.OrderNo;
import com.myshop.order.domain.OrderRepository;
import com.myshop.order.domain.OrderState;
import com.myshop.order.domain.Orderer;
import com.myshop.order.domain.ShippingInfo;
import com.myshop.order.query.dto.OrderRequest;

@Service
public class PlaceOrderService {

	private final OrderRepository repository;
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;

	public PlaceOrderService(OrderRepository repository, ProductRepository productRepository,
		MemberRepository memberRepository) {
		this.repository = repository;
		this.productRepository = productRepository;
		this.memberRepository = memberRepository;
	}

	@Transactional
	public OrderNo placeOrder(OrderRequest orderRequest) {
		List<ValidationError> errors = new ArrayList<>();
		if (orderRequest == null) {
			errors.add(ValidationError.of("empty"));
		}

		if (!errors.isEmpty()) {
			throw new ValidationErrorException(errors);
		}

		OrderNo orderNo = repository.nextId();
		Order order = createOrder(orderNo, orderRequest);
		repository.save(order);
		return orderNo;
	}

	private Order createOrder(OrderNo orderNo, OrderRequest orderRequest) {
		Member member = memberRepository.findById(orderRequest.getOrdererMemberId());
		Orderer orderer = new Orderer(member.getId(), member.getName());
		ShippingInfo shippingInfo = orderRequest.getShippingInfo();
		List<OrderLine> orderLines = new ArrayList<>();
		for (OrderProduct orderProduct : orderRequest.getOrderProducts()) {
			ProductId productId = new ProductId(orderProduct.getProductId());
			Product product = productRepository.findById(productId);
			Money price = product.getProductInfo().getPrice();
			int quantity = orderProduct.getQuantity();
			OrderLine orderLine = new OrderLine(productId, price, quantity);
			orderLines.add(orderLine);
		}
		return new Order(orderNo, orderer, orderLines, shippingInfo, OrderState.PAYMENT_WAITING);
	}
}
