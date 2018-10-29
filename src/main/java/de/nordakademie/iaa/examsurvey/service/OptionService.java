package de.nordakademie.iaa.examsurvey.service;

import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional(propagation = Propagation.REQUIRED)
public interface OptionService {
    void saveOptionsForSurvey(Set<Option> options, Survey survey);

    void updateOptionsForSurvey(Survey survey);

    void deleteAllOptionsForSurvey(Survey existentSurvey);
}
