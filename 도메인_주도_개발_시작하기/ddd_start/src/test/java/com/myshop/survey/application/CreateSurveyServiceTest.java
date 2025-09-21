package com.myshop.survey.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.myshop.survey.NoPermissionException;
import com.myshop.survey.infrastructure.FakeSurveyPermissionChecker;
import com.myshop.survey.query.dto.CreateSurveyRequest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreateSurveyServiceTest {

	@Autowired
	private CreateSurveyService service;

	@Autowired
	private FakeSurveyPermissionChecker fakeSurveyPermissionChecker;

	@AfterEach
	void tearDown() {
		fakeSurveyPermissionChecker.clear();
	}

	@Test
	void canCreated() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void createSurvey() {
		fakeSurveyPermissionChecker.allowUserCreation(1L);
		Long requesterId = 1L;
		CreateSurveyRequest request = new CreateSurveyRequest("Customer Satisfaction Survey", requesterId);

		Long surveyId = service.createSurvey(request);

		Assertions.assertThat(surveyId)
			.isNotNull()
			.isGreaterThan(0);
	}

	@Test
	void shouldThrowException_whenInvalidTitle() {
		Long requesterId = 1L;
		CreateSurveyRequest request = new CreateSurveyRequest(null, requesterId);

		Throwable throwable = Assertions.catchThrowable(() -> service.createSurvey(request));

		Assertions.assertThat(throwable)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("invalid title");
	}

	@Test
	void shouldThrowException_whenNoPermission() {
		Long requesterId = 1L;
		CreateSurveyRequest request = new CreateSurveyRequest("Customer Satisfaction Survey", requesterId);

		Throwable throwable = Assertions.catchThrowable(() -> service.createSurvey(request));

		Assertions.assertThat(throwable)
			.isInstanceOf(NoPermissionException.class)
			.hasMessageContaining("No permission to create survey");
	}
}
