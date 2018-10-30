package de.nordakademie.iaa.examsurvey.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Base Entity for notifications.
 *
 * @author Bengt-Lasse Arndt
 * @author Robert Peters
 */

@Entity
@Table(name = "notifications")
public class Notification extends AuditModel {
    private User user;
    private Survey survey;
    private NotificationType notificationType;

    public Notification() {
        // JPA constructor
    }

    public Notification(User user, Survey targetSurvey, NotificationType type) {
        this.user = user;
        this.survey = targetSurvey;
        this.notificationType = type;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    public Survey getSurvey() {
        return survey;
    }
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    public NotificationType getNotificationType() {
        return notificationType;
    }
    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
