package de.nordakademie.iaa.examsurvey.persistence.specification;

import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.SurveyStatus;
import de.nordakademie.iaa.examsurvey.domain.Survey_;
import de.nordakademie.iaa.examsurvey.domain.User;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specifications for {@link Survey}.
 *
 * @author Felix Plazek
 */
public class SurveySpecifications {
    private SurveySpecifications() {
        // prevent instantiation
    }

    /**
     * specifies surveys which the user is allowed to read. with initiator equals the user
     * or Status OPEN or CLOSED
     *
     * @param requestingUser to search for
     * @return specification for surveys visible for the passed user
     */
    public static Specification<Survey> isVisibleForUser(final User requestingUser) {
        return (Specification<Survey>) (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.equal(root.get(Survey_.initiator), requestingUser),
                root.get(Survey_.surveyStatus).in(SurveyStatus.OPEN, SurveyStatus.CLOSED));
    }

    /**
     * specifies the survey with given title
     *
     * @param title for searched survey
     * @return specification for a survey title
     */
    public static Specification<Survey> hasTitle(final String title) {
        return (Specification<Survey>) (root, query, criteriaBuilder) -> title == null
                ? criteriaBuilder.isNull(root.get(Survey_.title))
                : criteriaBuilder.equal(root.get(Survey_.title), title);
    }

    /**
     * specifies a survey with given id
     *
     * @param id for searched survey
     * @return specification for a survey title
     */
    public static Specification<Survey> hasId(final Long id) {
        return (Specification<Survey>) (root, query, criteriaBuilder) -> id == null
                ? criteriaBuilder.isNull(root.get(Survey_.id))
                : criteriaBuilder.equal(root.get(Survey_.id), id);
    }

    /**
     * specifies the survey with given title
     *
     * @param id of the survey
     * @return specification for a survey title
     */
    public static Specification<Survey> hasIdAndVisibleForUser(final Long id, final User requestingUser) {
        return (Specification<Survey>) (root, query, criteriaBuilder) -> criteriaBuilder.and(
                hasId(id).toPredicate(root, query, criteriaBuilder),
                isVisibleForUser(requestingUser).toPredicate(root, query, criteriaBuilder)
        );
    }
}
