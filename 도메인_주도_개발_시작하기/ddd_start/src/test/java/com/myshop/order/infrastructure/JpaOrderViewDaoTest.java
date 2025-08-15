package com.myshop.order.infrastructure;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.order.domain.OrderViewDao;
import com.myshop.order.query.dto.OrderView;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaOrderViewDaoTest {

	@Autowired
	private OrderViewDao orderViewDao;

	@Test
	void canCreated(){
		assertNotNull(orderViewDao);
	}

	@Test
	void shouldReturnOrderViewList(){
		String ordererId = "12345";

		List<OrderView> orderViews = orderViewDao.selectByOrderer(ordererId);

		assertNotNull(orderViews);
		assertThat(orderViews).isEmpty();
	}
}
