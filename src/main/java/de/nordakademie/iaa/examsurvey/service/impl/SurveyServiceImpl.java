package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.PermissionDeniedException;
import de.nordakademie.iaa.examsurvey.exception.SurveyAlreadyExistsException;
import de.nordakademie.iaa.examsurvey.exception.SurveyNotExistsException;
import de.nordakademie.iaa.examsurvey.persistence.OptionRepository;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.persistence.specification.SurveySpecifications;
import de.nordakademie.iaa.examsurvey.service.SurveyService;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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
    public List<Survey> loadAllSurveysForUser(User requestingUser) {
        return surveyRepository.findAll(isVisibleForUser(requestingUser));
    }

    @Override
    public Survey createSurvey(Survey survey, User initiator) {
        if (initiator == null) {
            throw new PermissionDeniedException("initiator must be non null");
        }
        survey.setInitiator(initiator);

        // if survey with title already exists; throw exeption
        surveyRepository.findOne(SurveySpecifications.hasTitle(survey.getTitle()))
                .orElseThrow(SurveyAlreadyExistsException::new);

        return surveyRepository.save(survey);
    }

    @Override
    public List<Option> saveOptionForSurvey(List<Option> options, String title, User initiator) {
        Survey survey = surveyRepository.findOne(SurveySpecifications.hasTitle(title))
                .orElseThrow(SurveyNotExistsException::new);
        if (initiator == null) {
            throw new PermissionDeniedException("initiator must be non null");
        } else if (!survey.getInitiator().equals(initiator)) {
            throw new PermissionDeniedException("User must be initiator of the survey");
        }
        for(Option option : options) {
            option.setSurvey(survey);
        }
        return Lists.newArrayList(optionRepository.saveAll(options));
    }


}
