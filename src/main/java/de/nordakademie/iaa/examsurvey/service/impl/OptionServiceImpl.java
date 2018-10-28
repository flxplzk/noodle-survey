package de.nordakademie.iaa.examsurvey.service.impl;

import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.persistence.OptionRepository;
import de.nordakademie.iaa.examsurvey.persistence.specification.OptionSpecifications;
import de.nordakademie.iaa.examsurvey.service.OptionService;

import java.util.List;

public class OptionServiceImpl implements OptionService {
    private final OptionRepository repository;

    public OptionServiceImpl(OptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveOptionsForSurvey(List<Option> options, Survey survey) {
        options.forEach(option -> {
            option.setSurvey(survey);
            option.setId(null);
        });
        repository.saveAll(options);
    }

    @Override
    public void deleteAllOptionsForSurvey(Survey survey) {
        List<Option> optionsToDelete = repository.findAll(OptionSpecifications.hasSurvey(survey));
        repository.deleteAll(optionsToDelete);
    }
}
