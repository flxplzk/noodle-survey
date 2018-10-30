package de.nordakademie.iaa.examsurvey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * BAse Entity for Events
 *
 * @author Felix Plazek
 */
@Entity
@Table(name = "event")
public class Event extends AuditModel {
    private String title;
    private Date time;
    private Survey survey;

    @Column(name = "event_title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_time", nullable = false)
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
