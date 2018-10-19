package de.nordakademie.iaa.examsurvey.persistence.specification;

import de.nordakademie.iaa.examsurvey.domain.Notification;
import de.nordakademie.iaa.examsurvey.domain.Notification_;
import de.nordakademie.iaa.examsurvey.domain.Survey_;
import de.nordakademie.iaa.examsurvey.domain.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class NotificationSpecifications {
    private NotificationSpecifications() {
        // prevent instantiation
    }

    public static Specification<Notification> byUser(User authenticatedUser) {
        return (Specification<Notification>) (root, query, criteriaBuilder) -> authenticatedUser == null
                ? criteriaBuilder.isNull(root.get(Notification_.user))
                : criteriaBuilder.equal(root.get(Notification_.user), authenticatedUser);
    }
}
