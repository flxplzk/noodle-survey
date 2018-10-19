package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.PermissionDeniedException;
import de.nordakademie.iaa.examsurvey.exception.SurveyAlreadyExistsException;
import de.nordakademie.iaa.examsurvey.exception.SurveyNotFoundException;
import de.nordakademie.iaa.examsurvey.persistence.OptionRepository;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.persistence.specification.OptionSpecifications;
import de.nordakademie.iaa.examsurvey.service.SurveyService;

import java.util.List;

import static de.nordakademie.iaa.examsurvey.persistence.specification.SurveySpecifications.hasTitle;
import static de.nordakademie.iaa.examsurvey.persistence.specification.SurveySpecifications.hasTitleAndVisibleForUser;
import static de.nordakademie.iaa.examsurvey.persistence.specification.SurveySpecifications.isVisibleForUser;

/**
 * UserService implementation.
 *
 * @author Robert Peters
 * @author Felix Plazek
 */
public class SurveyServiceImpl implements SurveyService {
    private final SurveyRepository surveyRepository;
    private final OptionRepository optionRepository;

    public SurveyServiceImpl(SurveyRepository surveyRepository, OptionRepository optionRepository) {
        this.surveyRepository = surveyRepository;
        this.optionRepository = optionRepository;
    }

    @Override
    public Survey createSurvey(Survey survey, User initiator) {
        requireNonNull(initiator);
        requireNonExistent(survey);

        survey.setInitiator(initiator);

        Survey createdSurvey = surveyRepository.save(survey);
        if (survey.getOptions().size() > 0) {
            saveOptionForSurveyClass(survey.getOptions(), createdSurvey);
        }
        return createdSurvey;
    }

    @Override
    public List<Option> saveOptionsForSurvey(List<Option> options, String surveyTitle, User requestingUser) {
        requireNonNull(requestingUser);
        Survey survey = surveyRepository.findOne(hasTitle(surveyTitle))
                .orElseThrow(SurveyNotFoundException::new);
        requireInitiator(requestingUser, survey);
        return saveOptionForSurveyClass(options, survey);
    }

    @Override
    public List<Option> loadAllOptionsForSurveyWithUser(String surveyTitle, User authenticatedUser) {
        Survey survey = surveyRepository.findOne(hasTitleAndVisibleForUser(surveyTitle, authenticatedUser))
                .orElseThrow(SurveyNotFoundException::new);

        return Lists.newArrayList(optionRepository.findAll(OptionSpecifications.hasSurvey(survey)));
    }

    @Override
    public List<Survey> loadAllSurveysWithUser(User requestingUser) {
        requireNonNull(requestingUser);
        return surveyRepository.findAll(isVisibleForUser(requestingUser));
    }

    private List<Option> saveOptionForSurveyClass(List<Option> options, Survey survey) {
        for(Option option : options) {
            option.setSurvey(survey);
        }
        return Lists.newArrayList(optionRepository.saveAll(options));
    }

    private void requireNonNull(User initiator) {
        if (initiator == null) {
            throw new PermissionDeniedException("initiator must be non null");
        }
    }

    private void requireNonExistent(Survey survey) {
        // if survey with title already exists; throw exception
        if (surveyRepository.findOne(hasTitle(survey.getTitle())).isPresent()){
            throw new SurveyAlreadyExistsException();
        }
    }

    private void requireInitiator(User requestingUser, Survey survey) {
        if (!survey.getInitiator().equals(requestingUser)) {
            throw new PermissionDeniedException("User must be initiator of the survey");
        }
    }
}

