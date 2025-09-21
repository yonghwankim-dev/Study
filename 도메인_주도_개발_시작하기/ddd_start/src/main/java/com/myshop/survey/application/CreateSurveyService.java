package com.myshop.survey.application;

import org.springframework.stereotype.Service;

import com.myshop.survey.query.dto.CreateSurveyRequest;

@Service
public class CreateSurveyService {
	public Long createSurvey(CreateSurveyRequest request) {
		return 1L;
	}
}
