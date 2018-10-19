package de.nordakademie.iaa.examsurvey.persistence.specification;

import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Option_;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import org.springframework.data.jpa.domain.Specification;

public class OptionSpecifications {
    private OptionSpecifications() {
        // prevent instantiation
    }
    public static Specification<Option> hasSurvey(final Survey survey) {
        return (Specification<Option>) (root, query, criteriaBuilder) ->
                 criteriaBuilder.equal(root.get(Option_.survey), survey);
    }
}
