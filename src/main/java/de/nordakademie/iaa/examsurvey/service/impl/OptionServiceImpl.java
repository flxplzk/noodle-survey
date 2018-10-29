package de.nordakademie.iaa.examsurvey.service.impl;

import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.persistence.OptionRepository;
import de.nordakademie.iaa.examsurvey.service.OptionService;

import java.util.List;
import java.util.Set;

import static de.nordakademie.iaa.examsurvey.persistence.specification.OptionSpecifications.hasSurvey;

public class OptionServiceImpl implements OptionService {
    private final OptionRepository repository;

    public OptionServiceImpl(OptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveOptionsForSurvey(Set<Option> options, Survey survey) {
        options.forEach(option -> {
            option.setSurvey(survey);
            option.setId(null);
        });
        repository.saveAll(options);
    }

    @Override
    public void updateOptionsForSurvey(Survey survey) {
        List<Option> persisted = repository.findAll(hasSurvey(survey));
        repository.deleteAll(persisted);
        // Thanks Hibernate still not getting why cascading from
        // survey entity does not work as expected.
        survey.getOptions().forEach(option -> {
            option.setSurvey(survey);
            option.setId(null);
        });
        repository.saveAll(survey.getOptions());
    }
}
