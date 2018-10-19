package de.nordakademie.iaa.examsurvey.service;

import de.nordakademie.iaa.examsurvey.domain.Notification;
import de.nordakademie.iaa.examsurvey.domain.NotificationType;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Service responsible for handling notifications.
 *
 * @author Robert Peters
 * @author Bengt-Lasse Arndt
 */
@Transactional(propagation = Propagation.REQUIRED)
public interface NotificationService {

    /**
     * reads all Notifications for the user.
     *
     * @param authenticatedUser must be not {@code null}
     * @return notifications for user
     */
    List<Notification> getNotificationsForUser(@NotNull User authenticatedUser);

    /**
     * creates new notifications for the given user with the {@link NotificationType}
     * for the passed users.
     *
     * @param type         of the notification
     * @param users        to notify
     * @param targetSurvey on which the user will be notified
     */
    void notifyUsersWithNotificationType(@NotNull NotificationType type,
                                         @NotNull List<User> users,
                                         @NotNull Survey targetSurvey);
}
