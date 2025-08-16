package com.myshop.catalog.application.product;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RegisterProductServiceTest {

	@Test
	void canCreated(){
		RegisterProductService service = new RegisterProductService();
		Assertions.assertThat(service).isNotNull();
	}

}
