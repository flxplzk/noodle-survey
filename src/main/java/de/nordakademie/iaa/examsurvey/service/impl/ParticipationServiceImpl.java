package de.nordakademie.iaa.examsurvey.service.impl;

import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.SurveyStatus;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.PermissionDeniedException;
import de.nordakademie.iaa.examsurvey.exception.SurveyNotOpenForParticipationException;
import de.nordakademie.iaa.examsurvey.persistence.ParticipationRepository;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.service.ParticipationService;

import javax.validation.constraints.NotNull;
import java.util.List;

import static de.nordakademie.iaa.examsurvey.persistence.specification.ParticipationSpecifications.withSurvey;
import static de.nordakademie.iaa.examsurvey.persistence.specification.ParticipationSpecifications.withSurveyAndUser;

public class ParticipationServiceImpl extends AbstractAuditModelService implements ParticipationService {
    private final ParticipationRepository participationRepository;

    public ParticipationServiceImpl(final SurveyRepository surveyRepository,
                                    final ParticipationRepository repository) {
        super(surveyRepository);
        this.participationRepository = repository;
    }

    @Override
    public void deleteAllParticipationsForSurvey(Survey survey) {
        List<Participation> participationsToDelete = participationRepository.findAll(withSurvey(survey));
        participationRepository.deleteAll(participationsToDelete);
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

    // #################################### VALIDATION METHODS #########################################

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

    private void requireOpenForParticipation(final Survey survey) {
        if (SurveyStatus.OPEN.equals(survey.getSurveyStatus())) {
            return;
        }
        throw new SurveyNotOpenForParticipationException("Participation for this Survey is not Allowed");
    }

}
