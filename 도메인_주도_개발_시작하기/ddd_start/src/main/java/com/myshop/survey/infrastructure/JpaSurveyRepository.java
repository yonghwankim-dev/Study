package com.myshop.survey.infrastructure;

import org.springframework.stereotype.Repository;

import com.myshop.survey.domain.Survey;
import com.myshop.survey.domain.SurveyRepository;

@Repository
public class JpaSurveyRepository implements SurveyRepository {

	private final SpringDataJpaSurveyRepository repository;

	public JpaSurveyRepository(SpringDataJpaSurveyRepository repository) {
		this.repository = repository;
	}

	@Override
	public Survey save(Survey survey) {
		return repository.save(survey);
	}
}
