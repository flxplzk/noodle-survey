package de.nordakademie.iaa.examsurvey.service;

import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.SurveyAlreadyExistsException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @author Robert Peters, Bengt-Lasse Arndt, Felix Plazek
 */
@Transactional(propagation = Propagation.REQUIRED)
public interface SurveyService {

    /**
     * Loads all surveys which are relevant for the given {@link User}.
     * Means: all surveys with {@link de.nordakademie.iaa.examsurvey.domain.SurveyStatus#OPEN}
     * or {@link de.nordakademie.iaa.examsurvey.domain.SurveyStatus#CLOSED} and all
     * survey where the user is the initiator with
     * {@link de.nordakademie.iaa.examsurvey.domain.SurveyStatus#PRIVATE}
     *
     * @param requestingUser
     * @return all surveys relevant for given {@link User}
     */
    List<Survey> loadAllSurveysForUser(final User requestingUser);

    /**
     * Creates survey. Title must be unique otherwise an exeption will be thrown
     *
     * @param survey    to create
     * @param initiator of the survey
     * @return persisted survey
     * @throws SurveyAlreadyExistsException if title is not unique
     */
    Survey createSurvey(final Survey survey, final User initiator);

    /**
     * Creates Option for survey
     *
     * @param options    to create
     * @param title      of the survey
     * @param user       initiator of the survey
     * @return persistent Options
     * TODO: ggf. exception
     */
    List<Option> saveOptionForSurvey(List<Option> options, String title, User user);

    List<Option> getOptionsForSurvey(String title);
}
