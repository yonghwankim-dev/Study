package com.myshop.survey.query.dto;

public class CreateSurveyRequest {
	private String title;
	private Long requesterId;

	public CreateSurveyRequest(String title, Long requesterId) {
		this.title = title;
		this.requesterId = requesterId;
	}

	public String getTitle() {
		return title;
	}

	public Long getRequesterId() {
		return requesterId;
	}
}
