package de.nordakademie.iaa.examsurvey.service.impl;

import de.nordakademie.iaa.examsurvey.domain.NotificationType;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.SurveyStatus;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.PermissionDeniedException;
import de.nordakademie.iaa.examsurvey.exception.SurveyAlreadyExistsException;
import de.nordakademie.iaa.examsurvey.exception.SurveyNotFoundException;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.service.NotificationService;
import de.nordakademie.iaa.examsurvey.service.OptionService;
import de.nordakademie.iaa.examsurvey.service.ParticipationService;
import de.nordakademie.iaa.examsurvey.service.SurveyService;

import java.util.List;

import static de.nordakademie.iaa.examsurvey.persistence.specification.SurveySpecifications.hasTitle;
import static de.nordakademie.iaa.examsurvey.persistence.specification.SurveySpecifications.isVisibleForUser;

/**
 * UserService implementation.
 *
 * @author Robert Peters
 * @author Felix Plazek
 */
public class SurveyServiceImpl extends AbstractAuditModelService implements SurveyService {
    private final NotificationService notificationService;
    private final OptionService optionService;
    private final ParticipationService participationService;

    public SurveyServiceImpl(final SurveyRepository surveyRepository,
                             final NotificationService notificationService,
                             final OptionService optionService,
                             final ParticipationService participationService) {
        super(surveyRepository);
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
        final Survey persistedSurvey = findModifiableSurveyWithInitiator(survey, authenticatedUser);
        participationService.deleteAllParticipationsForSurvey(survey);
        optionService.updateOptionsForSurvey(survey);
        notificationService.notifyUsersWithNotificationType(NotificationType.SURVEY_CHANGE, survey);
        // For not getting trouble with JPA, only modifiable field values are copied to the persisted survey
        persistedSurvey.setDescription(survey.getDescription());
        persistedSurvey.setSurveyStatus(survey.getSurveyStatus());
        return surveyRepository.save(persistedSurvey);
    }

    private Survey findModifiableSurveyWithInitiator(Survey survey, User authenticatedUser) {
        requireNonNullUser(authenticatedUser);
        final Survey persistedSurvey = getExistent(survey);
        requireInitiator(authenticatedUser, persistedSurvey);
        requireValidStatus(persistedSurvey.getSurveyStatus());
        if (isSurveyClose(survey, persistedSurvey)) {
            throw new PermissionDeniedException("Manual closing of survey prohibited. You must create an event for this Survey");
        }
        return persistedSurvey;
    }

    @Override
    public void closeSurvey(Survey surveyToClose, User authenticatedUser) {
        final Survey persistedSurvey = findModifiableSurveyWithInitiator(surveyToClose, authenticatedUser);
        persistedSurvey.setSurveyStatus(SurveyStatus.CLOSED);
        surveyRepository.save(persistedSurvey);
    }

    @Override
    public void deleteSurvey(Long id, User authenticatedUser) {
        final Survey existentSurvey = findDeletableSurveyWithInitiator(id, authenticatedUser);
        participationService.deleteAllParticipationsForSurvey(existentSurvey);
        optionService.deleteAllOptionsForSurvey(existentSurvey);
        notificationService.deleteAllNotificationsForSurvey(existentSurvey);
        surveyRepository.delete(existentSurvey);
    }

    private Survey findDeletableSurveyWithInitiator(Long id, User authenticatedUser) {
        requireNonNullUser(authenticatedUser);
        Survey existentSurvey = surveyRepository.findById(id)
                .orElseThrow(SurveyNotFoundException::new);
        requireInitiator(authenticatedUser, existentSurvey);
        return existentSurvey;
    }

    @Override
    public List<Survey> loadAllSurveysWithUser(User requestingUser) {
        requireNonNullUser(requestingUser);
        return surveyRepository.findAll(isVisibleForUser(requestingUser));
    }

    @Override
    public Survey loadSurveyWithUser(Long identifier, User authenticatedUser) {
        requireNonNullUser(authenticatedUser);
        return getSurveyVisibleForUser(identifier, authenticatedUser);
    }

    // ########################################## VALIDATION METHODS ###################################################

    private boolean isSurveyClose(Survey survey, Survey persistedSurvey) {
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
}

