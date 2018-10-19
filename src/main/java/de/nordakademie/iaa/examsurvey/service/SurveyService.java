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
     * Creates Option for survey
     *
     * @param options    to create
     * @param identifier of the survey
     * @param user       initiator of the survey
     * @return persistent Options
     */
    List<Option> saveOptionsForSurvey(@NotNull final List<Option> options,
                                      @NotNull final String identifier,
                                      @NotNull final User user);

    /**
     * retrieves all {@link Option}'s which are visible for the authenticated User.
     *
     * @param title             of the Survey
     * @param authenticatedUser which requests
     * @return list of options belonging to the survey
     */
    List<Option> loadAllOptionsForSurveyWithUser(@NotNull final String title,
                                                 @NotNull final User authenticatedUser);

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

    List<Participation> loadAllParticipationsForSurveyWithUser(String identifier, User authenticatedUser);
}
