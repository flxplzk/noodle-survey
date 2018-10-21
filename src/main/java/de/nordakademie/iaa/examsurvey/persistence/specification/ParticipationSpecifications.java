package de.nordakademie.iaa.examsurvey.persistence.specification;

import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Participation_;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class ParticipationSpecifications {
    public static Specification<Participation> withSurvey(final Survey survey) {
        return (Specification<Participation>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Participation_.survey), survey);
    }

    public static Specification<Participation> withSurveyAndUser(final Survey survey, final User user) {
        return (Specification<Participation>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(Participation_.survey), survey),
                        criteriaBuilder.equal(root.get(Participation_.user), user)
                );
    }
}
