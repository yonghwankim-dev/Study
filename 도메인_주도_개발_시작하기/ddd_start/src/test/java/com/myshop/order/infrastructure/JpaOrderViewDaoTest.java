package com.myshop.order.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.myshop.order.domain.OrderViewDao;

class JpaOrderViewDaoTest {

	@Test
	void canCreated(){
		OrderViewDao orderViewDao = new JpaOrderViewDao();
		assertNotNull(orderViewDao);
	}

}
