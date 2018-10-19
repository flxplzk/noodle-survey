package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Notification;
import de.nordakademie.iaa.examsurvey.domain.NotificationType;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.persistence.NotificationRepository;
import de.nordakademie.iaa.examsurvey.persistence.specification.NotificationSpecifications;
import de.nordakademie.iaa.examsurvey.service.NotificationService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class NotificationServiceImpl implements NotificationService{
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getNotificationsForUser(User authenticatedUser) {
        return Lists.newArrayList(notificationRepository
                .findAll(NotificationSpecifications.byUser(authenticatedUser)));
    }

    @Override
    public void notifyUsersWithNotificationType(@NotNull NotificationType type,
                                                @NotNull List<User> users,
                                                @NotNull Survey targetSurvey) {
        for (final User user : users) {
            notificationRepository.save(new Notification(user, targetSurvey, type));
        }
    }
}
