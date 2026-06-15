package com.example.userservice.service;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {

	private final Environment environment;

	public FeignErrorDecoder(Environment environment) {
		this.environment = environment;
	}

	@Override
	public Exception decode(String methodKey, Response response) {
		switch (response.status()){
			case 400:
				break;
			case 404:
				if(methodKey.contains("getOrders")){
					return new ResponseStatusException(HttpStatus.valueOf(response.status()), environment.getProperty("order_service.exception.orders-is-empty"));
				}
				break;
			default:
				return new Exception(response.reason());
		}
		return null;
	}
}
