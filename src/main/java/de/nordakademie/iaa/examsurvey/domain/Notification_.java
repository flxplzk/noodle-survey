package de.nordakademie.iaa.examsurvey.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Notification.class)
public class Notification_ {
    public static volatile SingularAttribute<Notification, Long> id;
    public static volatile SingularAttribute<Notification, User> user;
    public static volatile SingularAttribute<Notification, Survey> survey;
    public static volatile SingularAttribute<Notification, String> notificationText;
    public static volatile SingularAttribute<Notification, NotificationType> notificationType;
    public static volatile SingularAttribute<Notification, LocalDateTime> creationDate;
}
