package com.myshop.survey.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CreateSurveyServiceTest {

	@Test
	void canCreated() {
		CreateSurveyService service = new CreateSurveyService();

		Assertions.assertThat(service).isNotNull();
	}

}
