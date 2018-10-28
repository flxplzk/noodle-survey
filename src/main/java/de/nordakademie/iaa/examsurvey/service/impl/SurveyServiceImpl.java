package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.NotificationType;
import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.SurveyStatus;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.MissingInformationException;
import de.nordakademie.iaa.examsurvey.exception.PermissionDeniedException;
import de.nordakademie.iaa.examsurvey.exception.SurveyAlreadyExistsException;
import de.nordakademie.iaa.examsurvey.exception.SurveyNotFoundException;
import de.nordakademie.iaa.examsurvey.exception.SurveyNotOpenForParticipationException;
import de.nordakademie.iaa.examsurvey.persistence.OptionRepository;
import de.nordakademie.iaa.examsurvey.persistence.ParticipationRepository;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.persistence.specification.OptionSpecifications;
import de.nordakademie.iaa.examsurvey.service.EventService;
import de.nordakademie.iaa.examsurvey.service.NotificationService;
import de.nordakademie.iaa.examsurvey.service.OptionService;
import de.nordakademie.iaa.examsurvey.service.ParticipationService;
import de.nordakademie.iaa.examsurvey.service.SurveyService;

import java.util.List;

import static de.nordakademie.iaa.examsurvey.persistence.specification.ParticipationSpecifications.withSurvey;
import static de.nordakademie.iaa.examsurvey.persistence.specification.ParticipationSpecifications.withSurveyAndUser;
import static de.nordakademie.iaa.examsurvey.persistence.specification.SurveySpecifications.hasIdAndVisibleForUser;
import static de.nordakademie.iaa.examsurvey.persistence.specification.SurveySpecifications.hasTitle;
import static de.nordakademie.iaa.examsurvey.persistence.specification.SurveySpecifications.isVisibleForUser;

/**
 * UserService implementation.
 *
 * @author Robert Peters
 * @author Felix Plazek
 */
public class SurveyServiceImpl extends AbstractAuditModelService<Survey> implements SurveyService {
    private final SurveyRepository surveyRepository;
    private final OptionRepository optionRepository;
    private final ParticipationRepository participationRepository;
    private final NotificationService notificationService;
    private final OptionService optionService;
    private final ParticipationService participationService;

    public SurveyServiceImpl(final SurveyRepository surveyRepository,
                             final OptionRepository optionRepository,
                             final ParticipationRepository participationRepository,
                             final NotificationService notificationService,
                             OptionService optionService,
                             ParticipationService participationService) {
        this.surveyRepository = surveyRepository;
        this.optionRepository = optionRepository;
        this.participationRepository = participationRepository;
        this.notificationService = notificationService;
        this.optionService = optionService;
        this.participationService = participationService;
    }

    @Override
    public Survey createSurvey(Survey survey, User initiator) {
        requireNonNullUser(initiator);
        requireNonExistent(survey);
        survey.setInitiator(initiator);
        Survey createdSurvey = surveyRepository.save(survey);
        optionService.saveOptionsForSurvey(survey.getOptions(), createdSurvey);
        return createdSurvey;
    }

    @Override
    public Survey update(Survey survey, User authenticatedUser) {
        requireNonNullUser(authenticatedUser);
        final Survey persistedSurvey = getExistent(survey);
        requireInitiator(authenticatedUser, persistedSurvey);
        requireValidStatus(persistedSurvey.getSurveyStatus());
        survey.setInitiator(authenticatedUser);
        participationService.deleteAllParticipationsForSurvey(survey);
        optionService.deleteAllOptionsForSurvey(survey);
        optionService.saveOptionsForSurvey(survey.getOptions(), survey);
        notificationService.notifyUsersWithNotificationType(NotificationType.SURVEY_CHANGE, survey);

        if (isSurveyClose(survey, persistedSurvey)) {
           throw new PermissionDeniedException("Manual closing of survey prohibited");
        }

        return surveyRepository.save(survey);
    }

    @Override
    public void closeSurvey(Survey surveyToClose, User authenticatedUser) {
        requireNonNullUser(authenticatedUser);
        Survey existentSurvey = getExistent(surveyToClose);
        requireInitiator(authenticatedUser, existentSurvey);
        existentSurvey.setSurveyStatus(SurveyStatus.CLOSED);
        surveyRepository.save(existentSurvey);
    }

    @Override
    public List<Option> loadAllOptionsOfSurveyForUser(Long surveyTitle, User authenticatedUser) {
        Survey survey = getSurveyVisibleForUser(surveyTitle, authenticatedUser);
        return Lists.newArrayList(optionRepository.findAll(OptionSpecifications.hasSurvey(survey)));
    }

    @Override
    public List<Survey> loadAllSurveysWithUser(User requestingUser) {
        requireNonNullUser(requestingUser);
        return surveyRepository.findAll(isVisibleForUser(requestingUser));
    }

    @Override
    public List<Participation> loadAllParticipationsOfSurveyForUser(Long identifier, User authenticatedUser) {
        Survey survey = getSurveyVisibleForUser(identifier, authenticatedUser);
        return participationRepository.findAll(withSurvey(survey));
    }

    @Override
    public Participation saveParticipationForSurveyWithAuthenticatedUser(Participation participation,
                                                                         Long surveyId,
                                                                         User authenticatedUser) {
        requireNonNullUser(authenticatedUser);
        final Survey survey = getSurveyVisibleForUser(surveyId, authenticatedUser);
        requireNonInitiator(survey, authenticatedUser);

        requireOpenForParticipation(survey);
        participation = requireOne(survey, participation, authenticatedUser);

        return participationRepository.save(participation);
    }

    @Override
    public Survey loadSurveyWithUser(Long identifier, User authenticatedUser) {
        requireNonNullUser(authenticatedUser);
        return getSurveyVisibleForUser(identifier, authenticatedUser);
    }

    // ########################################## VALIDATION METHODS ###################################################


    /**
     * @param survey with new data
     * @param persistedSurvey to be overwritten
     * @return true if survey.getSurveyStatus() == CLOSED and persitedSurvey.getSurveyStatus() != CLOSED
     */
    private boolean isSurveyClose(Survey survey, Survey persistedSurvey) { // TODO clear
        return SurveyStatus.CLOSED.equals(survey.getSurveyStatus())
                && !SurveyStatus.CLOSED.equals(persistedSurvey.getSurveyStatus());
    }

    private void requireValidStatus(SurveyStatus persistedState) {
        if (SurveyStatus.CLOSED.equals(persistedState)) {
            throw new PermissionDeniedException("Surveys with status CLOSED may not be modified. Surveys must be closed us");
        }
    }

    private Survey getExistent(Survey survey) {
        return surveyRepository.findById(survey.getId())
                .orElseThrow(SurveyNotFoundException::new);
    }

    private void requireSelectedOption(Survey survey) {
        if (survey.getEvent() == null){
            throw new MissingInformationException("When closing a survey, selected Opten must not be null");
        }
    }

    private void requireNonInitiator(Survey survey, User authenticatedUser) {
        if (survey.getInitiator().equals(authenticatedUser)) {
            throw new PermissionDeniedException("Initiator may not participate to own survey");
        }
    }

    private Participation requireOne(final Survey survey, final Participation newParticipation, final User authenticatedUser) {
        Participation participation = participationRepository.findOne(withSurveyAndUser(survey, authenticatedUser))
                .orElse(newParticipation);
        participation.setUser(authenticatedUser);
        participation.setSurvey(survey);
        participation.setOptions(newParticipation.getOptions());
        return participation;
    }


    private Survey getSurveyVisibleForUser(final Long identifier, final User authenticatedUser) {
        return surveyRepository.findOne(hasIdAndVisibleForUser(identifier, authenticatedUser))
                .orElseThrow(SurveyNotFoundException::new);
    }

    private void requireNonExistent(final Survey survey) {
        // if survey with title already exists; throw exception
        if (surveyRepository.findOne(hasTitle(survey.getTitle())).isPresent()) {
            throw new SurveyAlreadyExistsException();
        }
    }

    private void requireInitiator(final User requestingUser, final Survey survey) {
        if (!survey.getInitiator().equals(requestingUser)) {
            throw new PermissionDeniedException("User must be initiator of the survey");
        }
    }

    private void requireOpenForParticipation(final Survey survey) {
        if (SurveyStatus.OPEN.equals(survey.getSurveyStatus())) {
            return;
        }
        throw new SurveyNotOpenForParticipationException("Participation for this Survey is not Allowed");
    }
}

