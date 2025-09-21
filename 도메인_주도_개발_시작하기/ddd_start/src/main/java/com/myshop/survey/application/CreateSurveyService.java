package com.myshop.survey.application;

import org.springframework.stereotype.Service;

import com.myshop.survey.domain.Survey;
import com.myshop.survey.domain.SurveyRepository;
import com.myshop.survey.domain.service.SurveyPermissionChecker;
import com.myshop.survey.query.dto.CreateSurveyRequest;

@Service
public class CreateSurveyService {

	private final SurveyRepository surveyRepository;
	private final SurveyPermissionChecker permissionChecker;

	public CreateSurveyService(SurveyRepository surveyRepository, SurveyPermissionChecker permissionChecker) {
		this.surveyRepository = surveyRepository;
		this.permissionChecker = permissionChecker;
	}

	public Long createSurvey(CreateSurveyRequest request) {
		validate(request);
		if (!permissionChecker.hasUserCreationPermission(request.getRequesterId())) {
			throw new IllegalArgumentException("no permission");
		}
		Survey survey = new Survey(request.getTitle());
		return surveyRepository.save(survey).getId();
	}

	private void validate(CreateSurveyRequest request) {
		String title = request.getTitle();
		if (title == null || title.isBlank()) {
			throw new IllegalArgumentException("invalid title");
		}
	}
}
