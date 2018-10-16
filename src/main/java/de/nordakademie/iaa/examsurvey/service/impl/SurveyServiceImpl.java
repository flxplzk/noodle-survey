package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.service.SurveyService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * UserService implementation.
 *
 * @author Robert Peters
 */
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository surveyRepository;

    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public List<Survey> loadAll() {
        return Lists.newArrayList(surveyRepository.findAll());
    }
}
