package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.MissingDataException;
import de.nordakademie.iaa.examsurvey.persistence.OptionRepository;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.persistence.specification.OptionSpecifications;
import de.nordakademie.iaa.examsurvey.service.OptionService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static de.nordakademie.iaa.examsurvey.persistence.specification.OptionSpecifications.hasSurvey;

/**
 * @author felix plazek
 * @author robert peters
 * @author bengt-lasse arndt
 * @author sascha pererva
 */
public class OptionServiceImpl
        extends AbstractAuditModelService
        implements OptionService {
    private final OptionRepository optionRepository;

    public OptionServiceImpl(final SurveyRepository surveyRepository,
                             final OptionRepository repository) {
        super(surveyRepository);
        this.optionRepository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOptionsForSurvey(final Survey survey) {
        if (survey.getOptions() == null) {
            throw new MissingDataException("User must provide at least one Option for a survey; aborted update!");
        }
        final Set<Long> updatedOptions = survey.getOptions().stream()
                .map(Option::getId)
                .collect(Collectors.toSet());

        // determine if the user deleted options
        final List<Option> toDelete = findOptionsBySurvey(survey).stream()
                .filter(option -> !updatedOptions.contains(option.getId()))
                .collect(Collectors.toList());
        // delete them
        optionRepository.deleteAll(toDelete);

        // create or update the others
        this.saveOptionsForSurvey(survey.getOptions(), survey);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveOptionsForSurvey(final Set<Option> options,
                                     final Survey survey) {
        options.forEach(option -> option.setSurvey(survey));
        optionRepository.saveAll(options);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllOptionsForSurvey(final Survey existentSurvey) {
        List<Option> persisted = findOptionsBySurvey(existentSurvey);
        optionRepository.deleteAll(persisted);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Option> loadAllOptionsOfSurveyForUser(final Long surveyId,
                                                      final User authenticatedUser) {
        final Survey survey = getSurveyVisibleForUser(surveyId, authenticatedUser);
        return Lists.newArrayList(optionRepository.findAll(OptionSpecifications.hasSurvey(survey)));
    }

    private List<Option> findOptionsBySurvey(final Survey survey) {
        return optionRepository.findAll(hasSurvey(survey));
    }
}
