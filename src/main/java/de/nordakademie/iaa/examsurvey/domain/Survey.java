package de.nordakademie.iaa.examsurvey.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Survey {
    private long id;
    private String title;
    private String description;
    private User initiator;
    private Option event;
    private Date creationDate;
    private SurveyStatus surveyStatus;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public User getInitiator() {
        return initiator;
    }

    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    public Option getEvent() {
        return event;
    }

    public void setEvent(Option event) {
        this.event = event;
    }

    @CreationTimestamp
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    public SurveyStatus getSurveyStatus() {
        return surveyStatus;
    }

    public void setSurveyStatus(SurveyStatus surveyStatus) {
        this.surveyStatus = surveyStatus;
    }
}
