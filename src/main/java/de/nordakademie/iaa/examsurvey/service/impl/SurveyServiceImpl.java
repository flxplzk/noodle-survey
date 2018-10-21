package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.PermissionDeniedException;
import de.nordakademie.iaa.examsurvey.exception.SurveyAlreadyExistsException;
import de.nordakademie.iaa.examsurvey.exception.SurveyNotFoundException;
import de.nordakademie.iaa.examsurvey.persistence.OptionRepository;
import de.nordakademie.iaa.examsurvey.persistence.ParticipationRepository;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.persistence.specification.OptionSpecifications;
import de.nordakademie.iaa.examsurvey.service.SurveyService;

import java.util.List;

import static de.nordakademie.iaa.examsurvey.persistence.specification.ParticipationSpecifications.withSurvey;
import static de.nordakademie.iaa.examsurvey.persistence.specification.ParticipationSpecifications.withSurveyAndUser;
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
    private final ParticipationRepository participationRepository;

    public SurveyServiceImpl(final SurveyRepository surveyRepository,
                             final OptionRepository optionRepository,
                             final ParticipationRepository participationRepository) {
        this.surveyRepository = surveyRepository;
        this.optionRepository = optionRepository;
        this.participationRepository = participationRepository;
    }

    @Override
    public Survey createSurvey(Survey survey, User initiator) {
        requireNonNull(initiator);
        requireNonExistent(survey);

        survey.setInitiator(initiator);

        Survey createdSurvey = surveyRepository.save(survey);
        if (survey.getOptions().size() > 0) {
            saveOptionsForSurvey(survey.getOptions(), createdSurvey);
        }
        return createdSurvey;
    }

    @Override
    public List<Option> saveOptionsForSurvey(List<Option> options, String surveyTitle, User requestingUser) {
        requireNonNull(requestingUser);
        Survey survey = requireSurveyWithInitiator(surveyTitle, requestingUser);
        return saveOptionsForSurvey(options, survey);
    }

    @Override
    public List<Option> loadAllOptionsForSurveyWithUser(String surveyTitle, User authenticatedUser) {
        Survey survey = requireSurveyVisibleForUser(surveyTitle, authenticatedUser);
        return Lists.newArrayList(optionRepository.findAll(OptionSpecifications.hasSurvey(survey)));
    }

    @Override
    public List<Survey> loadAllSurveysWithUser(User requestingUser) {
        requireNonNull(requestingUser);
        return surveyRepository.findAll(isVisibleForUser(requestingUser));
    }

    @Override
    public List<Participation> loadAllParticipationsForSurveyWithUser(String identifier, User authenticatedUser) {
        Survey survey = requireSurveyVisibleForUser(identifier, authenticatedUser);
        return participationRepository.findAll(withSurvey(survey));
    }

    @Override
    public Participation saveParticipationForSurveyWithAuthenticatedUser(Participation participation,
                                                                         String identifier,
                                                                         User authenticatedUser) {
        requireNonNull(authenticatedUser);
        Survey survey = requireSurveyVisibleForUser(identifier, authenticatedUser);

        requireNonInitiator(survey, authenticatedUser);
        participation = requireOne(survey, participation, authenticatedUser);

        return participationRepository.save(participation);
    }

    @Override
    public Survey loadSurveyWithUser(String identifier, User authenticatedUser) {
        requireNonNull(authenticatedUser);
        return requireSurveyVisibleForUser(identifier, authenticatedUser);
    }

    // ########################################## VALIDATION METHODS ###################################################

    private void requireNonInitiator(Survey survey, User authenticatedUser) {
        if (survey.getInitiator().equals(authenticatedUser)) {
            throw new PermissionDeniedException("Initiator may not participate to own survey");
        }
    }

    private Participation requireOne(Survey survey, Participation newParticipation, User authenticatedUser) {
        Participation participation = participationRepository.findOne(withSurveyAndUser(survey, authenticatedUser))
                .orElse(newParticipation);
        participation.setUser(authenticatedUser);
        participation.setSurvey(survey);
        participation.setOptions(newParticipation.getOptions());
        return participation;
    }


    private Survey requireSurveyVisibleForUser(String identifier, User authenticatedUser) {
        return surveyRepository.findOne(hasTitleAndVisibleForUser(identifier, authenticatedUser))
                .orElseThrow(SurveyNotFoundException::new);
    }


    private List<Option> saveOptionsForSurvey(List<Option> options, Survey survey) {
        options.forEach(option -> option.setSurvey(survey));
        return Lists.newArrayList(optionRepository.saveAll(options));
    }

    private Survey requireSurveyWithInitiator(String identifier, User authenticatedUser) {
        Survey survey = requireSurveyVisibleForUser(identifier, authenticatedUser);
        requireInitiator(authenticatedUser, survey);
        return survey;
    }

    private void requireNonNull(User initiator) {
        if (initiator == null) {
            throw new PermissionDeniedException("initiator must be non null");
        }
    }

    private void requireNonExistent(Survey survey) {
        // if survey with title already exists; throw exception
        if (surveyRepository.findOne(hasTitle(survey.getTitle())).isPresent()) {
            throw new SurveyAlreadyExistsException();
        }
    }

    private void requireInitiator(User requestingUser, Survey survey) {
        if (!survey.getInitiator().equals(requestingUser)) {
            throw new PermissionDeniedException("User must be initiator of the survey");
        }
    }
}

