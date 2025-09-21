package com.myshop.order.error;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public class PlaceOrderErrorResponse implements ErrorResponse {
	private final String field;
	private final String defaultMessage;
	private final String code;

	public PlaceOrderErrorResponse(String field, String defaultMessage, String code) {
		this.field = field;
		this.defaultMessage = defaultMessage;
		this.code = code;
	}

	@Override
	public HttpStatusCode getStatusCode() {
		return HttpStatusCode.valueOf(400);
	}

	@Override
	public ProblemDetail getBody() {
		return ProblemDetail.forStatusAndDetail(getStatusCode(), defaultMessage);
	}

	public String getField() {
		return field;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public String getCode() {
		return code;
	}
}
