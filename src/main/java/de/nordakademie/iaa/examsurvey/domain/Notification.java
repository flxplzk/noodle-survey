package de.nordakademie.iaa.examsurvey.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Base Entity for notifications.
 *
 * @author Bengt-Lasse Arndt, Robert Peters
 */

@Entity
public class Notification {
    private Long id;
    private User user;
    private LocalDateTime creationDate;
    private Survey survey;
    private NotificationType notificationType;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JsonIgnore
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @CreatedDate
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @ManyToOne
    public Survey getSurvey() {
        return survey;
    }
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "NOTIFICATION_TYPE")
    public NotificationType getNotificationType() {
        return notificationType;
    }
    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

}
