package de.nordakademie.iaa.examsurvey.persistence;

import de.nordakademie.iaa.examsurvey.domain.Notification;
import de.nordakademie.iaa.examsurvey.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link Notification}.
 *
 * @author Felix Plazek
 */
public interface NotificationRepository extends CrudRepository<Notification, Long>,
        JpaSpecificationExecutor<Notification> {
}
