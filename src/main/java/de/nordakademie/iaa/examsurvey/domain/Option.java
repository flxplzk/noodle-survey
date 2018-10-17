package de.nordakademie.iaa.examsurvey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Base Entity for Date-Options toDateTime choose from within a Survey.
 *
 * @author Bengt-Lasse Arndt, Robert Peters
 */
@Entity
@Table(name = "Option")
public class Option {
    private Long id;
    private LocalDateTime fromDatetime;
    private LocalDateTime toDateTime;
    private Survey survey;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public LocalDateTime getFromDatetime() {
        return fromDatetime;
    }
    public void setFromDatetime(LocalDateTime fromDatetime) {
        this.fromDatetime = fromDatetime;
    }

    @Column(nullable = true)
    public LocalDateTime getToDatetime() {
        return toDateTime;
    }
    public void setToDatetime(LocalDateTime toDateTime) {
        this.toDateTime = toDateTime;
    }

    @ManyToOne
    public Survey getSurvey() {
        return survey;
    }
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}
