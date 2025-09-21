package com.myshop.survey.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myshop.survey.domain.Survey;

public interface SpringDataJpaSurveyRepository extends JpaRepository<Survey, Long> {
	
}
