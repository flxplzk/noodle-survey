package de.nordakademie.iaa.examsurvey.persistence.specification;

import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Participation_;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ParticipationSpecifications {
    public static Specification<Participation> withSurvey(final Survey survey){
        return (Specification<Participation>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Participation_.survey), survey);
    }
}
