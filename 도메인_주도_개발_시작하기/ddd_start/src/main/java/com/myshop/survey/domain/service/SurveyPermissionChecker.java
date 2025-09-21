package com.myshop.survey.domain.service;

public interface SurveyPermissionChecker {
	boolean hasUserCreationPermission(Long userId);
}
