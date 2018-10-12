package de.nordakademie.iaa.examsurvey.persistence;

import de.nordakademie.iaa.examsurvey.domain.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
