package de.nordakademie.iaa.examsurvey.service.impl;

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
        Iterable<Survey> source = surveyRepository.findAll();
        List<Survey> target = new ArrayList<Survey>();
        source.forEach(target::add);
        return target;
    }
}
