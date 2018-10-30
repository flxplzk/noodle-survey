package de.nordakademie.iaa.examsurvey.service;

import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Transactional(propagation = Propagation.REQUIRED)
public interface OptionService {
    /**
     * retrieves all {@link Option}'s which are visible for the authenticated User.
     *
     * @param surveyId          of the Survey
     * @param authenticatedUser which requests
     * @return list of options belonging to the survey
     */
    List<Option> loadAllOptionsOfSurveyForUser(@NotNull Long surveyId, @NotNull User authenticatedUser);

    void saveOptionsForSurvey(Set<Option> options, Survey survey);

    void updateOptionsForSurvey(Survey survey);

    void deleteAllOptionsForSurvey(Survey existentSurvey);
}
