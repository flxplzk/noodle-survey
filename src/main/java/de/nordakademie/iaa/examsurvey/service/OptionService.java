package de.nordakademie.iaa.examsurvey.service;

import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
public interface OptionService {
    void saveOptionsForSurvey(List<Option> options, Survey survey);

    void deleteAllOptionsForSurvey(Survey survey);
}

/**
 private void saveOptionsForSurvey(List<Option> options, Survey survey) {
 options.forEach(option -> option.setSurvey(survey));
 return Lists.newArrayList(optionRepository.saveAll(options));
 }

 */