package de.nordakademie.iaa.examsurvey.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Base Entity for Survey, containing basic information.
 *
 * @author Bengt-Lasse Arndt, Robert Peters
 */
@Entity
@Table(name = "Survey")
public class Survey {
    private Long id;
    private String title;
    private String description;
    private User initiator;
    private Option event;
    private LocalDateTime creationDate;
    private SurveyStatus surveyStatus;
    private SurveyType surveyType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @NaturalId
    @Column(nullable = false)
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    public User getInitiator() {
        return initiator;
    }
    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    @ManyToOne
    public Option getEvent() {
        return event;
    }
    public void setEvent(Option event) {
        this.event = event;
    }

    @CreatedDate
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    public SurveyType getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(SurveyType surveyType) {
        this.surveyType = surveyType;
    }
}
