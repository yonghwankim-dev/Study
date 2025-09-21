package com.myshop.survey.domain.service;

import java.util.HashSet;
import java.util.Set;

public class FakeSurveyPermissionChecker implements SurveyPermissionChecker {

	private final Set<Long> creatableUserIds = new HashSet<>();

	public void allowUserCreation(Long userId) {
		creatableUserIds.add(userId);
	}

	@Override
	public boolean hasUserCreationPermission(Long userId) {
		return creatableUserIds.contains(userId);
	}
}
