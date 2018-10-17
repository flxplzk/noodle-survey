package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.SurveyStatus;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.PermissionDeniedException;
import de.nordakademie.iaa.examsurvey.exception.SurveyAlreadyExistsException;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.service.SurveyService;

import java.util.ArrayList;
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
    public List<Survey> loadAllSurveysForUser(User requestingUser) {
        ArrayList<SurveyStatus> statuses = Lists.newArrayList(SurveyStatus.CLOSED, SurveyStatus.OPEN);
        List<Survey> surveys = surveyRepository
                .findAllByInitiatorEqualsAndSurveyStatusEqualsOrSurveyStatusIn(requestingUser,
                        SurveyStatus.PRIVATE, statuses);
        return Lists.newArrayList(surveys);
    }

    @Override
    public Survey createSurvey(Survey survey, User initiator) {
        if (initiator == null) {
            throw new PermissionDeniedException("initiator must be non null");
        }
        survey.setInitiator(initiator);

        if (surveyRepository.findByTitle(survey.getTitle()).isPresent()) {
            throw new SurveyAlreadyExistsException();
        }
        return surveyRepository.save(survey);
    }
}
