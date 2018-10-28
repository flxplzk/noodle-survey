package de.nordakademie.iaa.examsurvey.service.impl;

import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.persistence.ParticipationRepository;
import de.nordakademie.iaa.examsurvey.service.ParticipationService;

import javax.validation.constraints.NotNull;
import java.util.List;

import static de.nordakademie.iaa.examsurvey.persistence.specification.ParticipationSpecifications.withSurvey;

public class ParticipationServiceImpl implements ParticipationService {
    private final ParticipationRepository repository;

    public ParticipationServiceImpl(ParticipationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Participation> loadAllParticipationsForSurvey(@NotNull Survey survey) {
        return repository.findAll(withSurvey(survey));
    }

    @Override
    public void deleteAllParticipationsForSurvey(Survey survey) {
        List<Participation> participationsToDelete = repository.findAll(withSurvey(survey));
        repository.deleteAll(participationsToDelete);
    }
}
