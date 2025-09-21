package com.myshop.survey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.myshop.survey.domain.service.FakeSurveyPermissionChecker;

@Configuration
public class SurveyConfig {
	@Bean
	public FakeSurveyPermissionChecker fakeSurveyPermissionChecker() {
		return new FakeSurveyPermissionChecker();
	}
}
