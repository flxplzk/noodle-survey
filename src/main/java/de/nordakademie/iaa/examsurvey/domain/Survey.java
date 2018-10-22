package de.nordakademie.iaa.examsurvey.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * Base Entity for Survey, containing basic information.
 *
 * @author Bengt-Lasse Arndt, Robert Peters
 */
@Entity
@Table(name = "surveys")
public class Survey extends AuditModel {
    private String title;
    private String description;
    private User initiator;
    private Option event;
    private SurveyStatus surveyStatus;
    private List<Option> options;

    @NaturalId
    @Size(max = 50)
    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Size(max = 1000)
    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    public User getInitiator() {
        return initiator;
    }
    public void setInitiator(User initiator) {
        this.initiator = initiator;
    }

    @ManyToOne
    @Deprecated
    public Option getEvent() {
        return event;
    }
    public void setEvent(Option event) {
        this.event = event;
    }

    @Column(name = "survey_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    public SurveyStatus getSurveyStatus() {
        return surveyStatus;
    }
    public void setSurveyStatus(SurveyStatus surveyStatus) {
        this.surveyStatus = surveyStatus;
    }

    @Transient
    @JsonProperty(access = WRITE_ONLY)
    public List<Option> getOptions() {
        return options;
    }
    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
