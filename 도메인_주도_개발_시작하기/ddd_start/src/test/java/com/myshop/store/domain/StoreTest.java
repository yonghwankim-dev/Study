package com.myshop.store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StoreTest {

	@Test
	void canCreated() {
		Store store = new Store(new StoreId("123456789"));

		Assertions.assertThat(store).isNotNull();
	}

}
