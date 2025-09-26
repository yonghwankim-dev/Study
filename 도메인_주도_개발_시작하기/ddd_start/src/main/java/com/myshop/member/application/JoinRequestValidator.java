package com.myshop.member.application;

import java.util.regex.Pattern;

import com.myshop.member.error.EmptyPropertyException;
import com.myshop.member.error.InvalidPropertyException;
import com.myshop.member.query.dto.JoinRequest;

public class JoinRequestValidator {
	public void validate(JoinRequest joinRequest) throws EmptyPropertyException, InvalidPropertyException {
		String name = joinRequest.getName();
		String address1 = joinRequest.getAddress1();
		String address2 = joinRequest.getAddress2();
		String zipCode = joinRequest.getZipCode();
		String rawPassword = joinRequest.getPassword();
		String email = joinRequest.getEmail();

		// 값의 형식 검사
		checkEmpty(name, "name");
		checkEmpty(address1, "address1");
		checkEmpty(address2, "address2");
		checkEmpty(zipCode, "zipCode");
		checkEmpty(rawPassword, "password");
		checkEmpty(email, "email");

		// name: 한글 또는 영문 대소문자, 공백 허용, 2~20자
		Pattern namePattern = Pattern.compile("^[a-zA-Z가-힣\\s]{2,20}$");
		checkFormat(name, "name", namePattern);

		// address1: 특수문자 제외 5~100자
		Pattern address1Pattern = Pattern.compile("^[a-zA-Z0-9가-힣\\s]{5,100}$");
		checkFormat(address1, "address1", address1Pattern);

		// address2: 특수문자 제외 0~100자
		Pattern address2Pattern = Pattern.compile("^[a-zA-Z0-9가-힣\\s]{0,100}$");
		checkFormat(address2, "address2", address2Pattern);

		// zipCode: 숫자 5자리
		Pattern zipCodePattern = Pattern.compile("^\\d{5}$");
		checkFormat(zipCode, "zipCode", zipCodePattern);

		// password: 영문, 숫자, 특수문자 조합 8~20자
		Pattern passwordPattern = Pattern.compile(
			"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,20}$");
		checkFormat(rawPassword, "password", passwordPattern);

		// email: 일반적인 이메일 형식
		Pattern emailPattern = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
		checkFormat(email, "email", emailPattern);
	}

	private void checkEmpty(String value, String propertyName) {
		if (value == null || value.isEmpty()) {
			throw new EmptyPropertyException(propertyName);
		}
	}

	private void checkFormat(String value, String propertyName, Pattern pattern) {
		if (!pattern.matcher(value).matches()) {
			throw new InvalidPropertyException(propertyName);
		}
	}
}
