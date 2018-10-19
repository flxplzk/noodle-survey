package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Notification;
import de.nordakademie.iaa.examsurvey.domain.NotificationType;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.persistence.NotificationRepository;
import de.nordakademie.iaa.examsurvey.persistence.specification.NotificationSpecifications;
import de.nordakademie.iaa.examsurvey.service.NotificationService;

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
    public void notifyUsersWithNotificationType(NotificationType type, List<User> users) {
        List<Notification> notifications = new ArrayList<>();
        Notification note = new Notification();
        note.setNotificationType(type);
        for (User user: users) {
            note.setUser(user);
            notifications.add(note);
        }
        notificationRepository.saveAll(notifications);
    }
}
