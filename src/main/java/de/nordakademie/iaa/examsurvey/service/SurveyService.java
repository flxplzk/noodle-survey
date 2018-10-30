package de.nordakademie.iaa.examsurvey.service;

import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.SurveyAlreadyExistsException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Robert Peters, Bengt-Lasse Arndt, Felix Plazek
 */
@Transactional(propagation = Propagation.REQUIRED)
public interface SurveyService {
    /**
     * Creates survey. Title must be unique otherwise an exeption will be thrown
     *
     * @param survey    to create
     * @param initiator of the survey
     * @return persisted survey
     * @throws SurveyAlreadyExistsException if title is not unique
     */
    Survey createSurvey(@NotNull final Survey survey,
                        @NotNull final User initiator);

    /**
     * Loads all surveys which are relevant for the given {@link User}.
     * Means: all surveys with {@link de.nordakademie.iaa.examsurvey.domain.SurveyStatus#OPEN}
     * or {@link de.nordakademie.iaa.examsurvey.domain.SurveyStatus#CLOSED} and all
     * survey where the user is the initiator with
     * {@link de.nordakademie.iaa.examsurvey.domain.SurveyStatus#PRIVATE}
     *
     * @param requestingUser which requests
     * @return all surveys relevant for given {@link User}
     */
    List<Survey> loadAllSurveysWithUser(@NotNull final User requestingUser);

    Survey loadSurveyWithUser(@NotNull Long identifier,
                              @NotNull User authenticatedUser);

    Survey update(@NotNull Survey survey, @NotNull User authenticatedUser);

    void closeSurvey(final Survey surveyToClose, User authenticatedUser);

    void deleteSurvey(Long id, User authenticatedUser);
}
