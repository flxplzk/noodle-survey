package de.nordakademie.iaa.examsurvey.service;

import de.nordakademie.iaa.examsurvey.domain.Notification;
import de.nordakademie.iaa.examsurvey.domain.NotificationType;
import de.nordakademie.iaa.examsurvey.domain.User;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotifications(User authenticatedUser);
    void notifyUsers(NotificationType type, List<User> users);
}
