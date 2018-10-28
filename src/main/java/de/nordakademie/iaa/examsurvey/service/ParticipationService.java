package de.nordakademie.iaa.examsurvey.service;

import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Service for operations concerning {@link Participation}
 */
@Transactional(propagation = Propagation.REQUIRED)
public interface ParticipationService {

    List<Participation> loadAllParticipationsForSurvey(@NotNull final Survey survey);

    void deleteAllParticipationsForSurvey(Survey survey);
}
