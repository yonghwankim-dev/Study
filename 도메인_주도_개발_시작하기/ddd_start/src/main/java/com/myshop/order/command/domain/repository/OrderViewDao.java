package com.myshop.order.command.domain.repository;

import java.util.List;

import com.myshop.order.command.domain.model.OrderNo;
import com.myshop.order.query.dto.OrderView;

public interface OrderViewDao {

	List<OrderView> selectByOrderer(String ordererId);

	OrderView findByOrderNo(OrderNo orderNo);
}
