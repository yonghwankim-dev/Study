package com.myshop.common.jpa;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.myshop.common.model.Email;
import com.myshop.common.model.EmailSet;

import jakarta.persistence.AttributeConverter;

public class EmailSetConverter implements AttributeConverter<EmailSet, String> {
	@Override
	public String convertToDatabaseColumn(EmailSet attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getEmails().stream()
			.map(Email::getAddress)
			.collect(Collectors.joining(","));
	}

	@Override
	public EmailSet convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		String[] emails = dbData.split(",");
		Set<Email> emailSet = Arrays.stream(emails)
			.map(Email::new)
			.collect(Collectors.toSet());
		return new EmailSet(emailSet);
	}
}
