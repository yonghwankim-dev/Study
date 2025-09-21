package com.myshop.survey.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.survey.query.dto.CreateSurveyRequest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreateSurveyServiceTest {

	@Autowired
	private CreateSurveyService service;

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void createSurvey() {
		CreateSurveyRequest request = new CreateSurveyRequest("Customer Satisfaction Survey");

		Long surveyId = service.createSurvey(request);

		Assertions.assertThat(surveyId)
			.isNotNull()
			.isGreaterThan(0);
	}

	@Test
	void shouldThrowException_whenInvalidTitle() {
		CreateSurveyRequest request = new CreateSurveyRequest(null);

		Throwable throwable = Assertions.catchThrowable(() -> service.createSurvey(request));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("invalid title");
	}
}
