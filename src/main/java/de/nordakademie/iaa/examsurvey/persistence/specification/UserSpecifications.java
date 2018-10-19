package de.nordakademie.iaa.examsurvey.persistence.specification;

import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.domain.User_;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    private UserSpecifications() {
        // prevent instantiation
    }

    public static Specification<User> hasUsername(final String username) {
        return (Specification<User>)
                (root, query, criteriaBuilder) -> username == null
                        ? criteriaBuilder.isNull(root.get(User_.username))
                        : criteriaBuilder.equal(root.get(User_.username), username);
    }
}
