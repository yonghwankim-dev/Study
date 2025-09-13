package com.myshop.member.query.dto;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public class JoinErrorResponse implements ErrorResponse {

	private final String field;
	private final String message;
	private final String code;

	public JoinErrorResponse(String field, String message, String code) {
		this.field = field;
		this.message = message;
		this.code = code;
	}

	@Override
	public HttpStatusCode getStatusCode() {
		return HttpStatusCode.valueOf(400);
	}

	@Override
	public ProblemDetail getBody() {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(getStatusCode(), message);
		problemDetail.setProperty("field", field);
		problemDetail.setProperty("code", code);
		return problemDetail;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}
}
