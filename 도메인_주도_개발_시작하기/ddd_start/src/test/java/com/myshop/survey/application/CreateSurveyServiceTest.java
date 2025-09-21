package com.myshop.survey.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.myshop.survey.query.dto.CreateSurveyRequest;

class CreateSurveyServiceTest {

	@Test
	void canCreated() {
		CreateSurveyService service = new CreateSurveyService();

		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void createSurvey() {
		CreateSurveyService service = new CreateSurveyService();
		CreateSurveyRequest request = new CreateSurveyRequest();

		Long surveyId = service.createSurvey(request);

		Assertions.assertThat(surveyId)
			.isNotNull()
			.isGreaterThan(0);
	}
}
