package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.SurveyStatus;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.PermissionDeniedException;
import de.nordakademie.iaa.examsurvey.exception.SurveyAlreadyExistsException;
import de.nordakademie.iaa.examsurvey.exception.SurveyNotFoundException;
import de.nordakademie.iaa.examsurvey.exception.SurveyNotOpenForParticipationException;
import de.nordakademie.iaa.examsurvey.persistence.OptionRepository;
import de.nordakademie.iaa.examsurvey.persistence.ParticipationRepository;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.persistence.specification.OptionSpecifications;
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

    public SurveyServiceImpl(final SurveyRepository surveyRepository,
                             final OptionRepository optionRepository,
                             final ParticipationRepository participationRepository) {
        this.surveyRepository = surveyRepository;
        this.optionRepository = optionRepository;
        this.participationRepository = participationRepository;
    }

    @Override
    public Survey createSurvey(Survey survey, User initiator) {
        requireNonNullUser(initiator);
        requireNonExistent(survey);

        survey.setInitiator(initiator);

        Survey createdSurvey = surveyRepository.save(survey);
        saveOptionsForSurvey(survey.getOptions(), createdSurvey);

        return createdSurvey;
    }

    @Override
    public List<Option> saveOptionsForSurvey(List<Option> options, Long surveyTitle, User requestingUser) {
        requireNonNullUser(requestingUser);
        Survey survey = requireSurveyWithInitiator(surveyTitle, requestingUser);
        return saveOptionsForSurvey(options, survey);
    }

    @Override
    public List<Option> loadAllOptionsOfSurveyForUser(Long surveyTitle, User authenticatedUser) {
        Survey survey = requireSurveyVisibleForUser(surveyTitle, authenticatedUser);
        return Lists.newArrayList(optionRepository.findAll(OptionSpecifications.hasSurvey(survey)));
    }

    @Override
    public List<Survey> loadAllSurveysWithUser(User requestingUser) {
        requireNonNullUser(requestingUser);
        return surveyRepository.findAll(isVisibleForUser(requestingUser));
    }

    @Override
    public List<Participation> loadAllParticipationsOfSurveyForUser(Long identifier, User authenticatedUser) {
        Survey survey = requireSurveyVisibleForUser(identifier, authenticatedUser);
        return participationRepository.findAll(withSurvey(survey));
    }

    @Override
    public Participation saveParticipationForSurveyWithAuthenticatedUser(Participation participation,
                                                                         Long surveyId,
                                                                         User authenticatedUser) {
        requireNonNullUser(authenticatedUser);
        final Survey survey = requireSurveyVisibleForUser(surveyId, authenticatedUser);
        requireNonInitiator(survey, authenticatedUser);

        requireOpenForParticipation(survey);
        participation = requireOne(survey, participation, authenticatedUser);

        return participationRepository.save(participation);
    }

    @Override
    public Survey loadSurveyWithUser(Long identifier, User authenticatedUser) {
        requireNonNullUser(authenticatedUser);
        return requireSurveyVisibleForUser(identifier, authenticatedUser);
    }

    private List<Option> saveOptionsForSurvey(List<Option> options, Survey survey) {
        options.forEach(option -> option.setSurvey(survey));
        return Lists.newArrayList(optionRepository.saveAll(options));
    }

    // ########################################## VALIDATION METHODS ###################################################

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


    private Survey requireSurveyVisibleForUser(final Long identifier, final User authenticatedUser) {
        return surveyRepository.findOne(hasIdAndVisibleForUser(identifier, authenticatedUser))
                .orElseThrow(SurveyNotFoundException::new);
    }

    private Survey requireSurveyWithInitiator(final Long identifier, final User authenticatedUser) {
        Survey survey = requireSurveyVisibleForUser(identifier, authenticatedUser);
        requireInitiator(authenticatedUser, survey);
        return survey;
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

