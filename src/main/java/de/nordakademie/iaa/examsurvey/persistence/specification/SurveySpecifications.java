package de.nordakademie.iaa.examsurvey.persistence.specification;

import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.SurveyStatus;
import de.nordakademie.iaa.examsurvey.domain.Survey_;
import de.nordakademie.iaa.examsurvey.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class SurveySpecifications {
    private SurveySpecifications() {
        // prevent instantiation
    }

    public static Specification<Survey> isVisibleForUser(final User requestingUser) {
        return (Specification<Survey>) (root, query, criteriaBuilder) -> criteriaBuilder
                .or(
                        criteriaBuilder.equal(root.get(Survey_.initiator), requestingUser),
                        root.get(Survey_.surveyStatus).in(SurveyStatus.OPEN, SurveyStatus.CLOSED)
                );


    }
}
