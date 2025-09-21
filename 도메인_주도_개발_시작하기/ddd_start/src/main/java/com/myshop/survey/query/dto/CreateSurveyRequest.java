package com.myshop.survey.query.dto;

public class CreateSurveyRequest {
	private String title;

	public CreateSurveyRequest(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
