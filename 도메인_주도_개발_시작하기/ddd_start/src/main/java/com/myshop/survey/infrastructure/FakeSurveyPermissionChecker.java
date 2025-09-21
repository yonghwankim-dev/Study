package com.myshop.survey.infrastructure;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.myshop.survey.domain.service.SurveyPermissionChecker;

@Component
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
