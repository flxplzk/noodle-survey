package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.persistence.OptionRepository;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.persistence.specification.OptionSpecifications;
import de.nordakademie.iaa.examsurvey.service.OptionService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static de.nordakademie.iaa.examsurvey.persistence.specification.OptionSpecifications.hasSurvey;

public class OptionServiceImpl extends AbstractAuditModelService implements OptionService {
    private final OptionRepository optionRepository;

    public OptionServiceImpl(final SurveyRepository surveyRepository, final OptionRepository repository) {
        super(surveyRepository);
        this.optionRepository = repository;
    }

    @Override
    public void updateOptionsForSurvey(Survey survey) {
        final Set<Long> updatedOptions = survey.getOptions().stream()
                .map(Option::getId)
                .collect(Collectors.toSet());

        final List<Option> toDelete = findOptionsBySurvey(survey).stream()
                .filter(option -> !updatedOptions.contains(option.getId()))
                .collect(Collectors.toList());

        optionRepository.deleteAll(toDelete);
        this.saveOptionsForSurvey(survey.getOptions(), survey);
    }

    @Override
    public void saveOptionsForSurvey(Set<Option> options, Survey survey) {
        options.forEach(option -> option.setSurvey(survey));
        optionRepository.saveAll(options);
    }

    @Override
    public void deleteAllOptionsForSurvey(Survey existentSurvey) {
        List<Option> persisted = findOptionsBySurvey(existentSurvey);
        optionRepository.deleteAll(persisted);
    }

    @Override
    public List<Option> loadAllOptionsOfSurveyForUser(Long surveyId, User authenticatedUser) {
        final Survey survey = getSurveyVisibleForUser(surveyId, authenticatedUser);
        return Lists.newArrayList(optionRepository.findAll(OptionSpecifications.hasSurvey(survey)));
    }

    private List<Option> findOptionsBySurvey(final Survey survey) {
        return optionRepository.findAll(hasSurvey(survey));
    }
}
