package de.nordakademie.iaa.examsurvey.service.impl;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Notification;
import de.nordakademie.iaa.examsurvey.domain.NotificationType;
import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.persistence.NotificationRepository;
import de.nordakademie.iaa.examsurvey.persistence.ParticipationRepository;
import de.nordakademie.iaa.examsurvey.persistence.specification.NotificationSpecifications;
import de.nordakademie.iaa.examsurvey.service.NotificationService;

import javax.validation.constraints.NotNull;
import java.util.List;

import static de.nordakademie.iaa.examsurvey.persistence.specification.NotificationSpecifications.byUser;
import static de.nordakademie.iaa.examsurvey.persistence.specification.ParticipationSpecifications.bySurvey;

/**
 * @author felix plazek
 * @author robert peters
 * @author bengt-lasse arndt
 * @author sascha pererva
 */
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final ParticipationRepository participationRepository;

    public NotificationServiceImpl(final NotificationRepository notificationRepository,
                                   final ParticipationRepository participationRepository) {
        this.notificationRepository = notificationRepository;
        this.participationRepository = participationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Notification> getNotificationsForUser(User authenticatedUser) {
        return Lists.newArrayList(notificationRepository
                .findAll(byUser(authenticatedUser)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyUsersWithNotificationType(@NotNull NotificationType type,
                                                @NotNull Survey targetSurvey) {
        final List<Participation> participationsOfSurvey = participationRepository.findAll(bySurvey(targetSurvey));
        final List<Notification> notifications = Lists.newArrayList();
        participationsOfSurvey.forEach(participation ->
                notifications.add(new Notification(participation.getUser(), targetSurvey, type))
        );
        notificationRepository.saveAll(notifications);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllNotificationsForSurvey(@NotNull Survey survey) {
        final List<Notification> notifications = notificationRepository
                .findAll(NotificationSpecifications.bySurvey(survey));
        notificationRepository.deleteAll(notifications);
    }
}
