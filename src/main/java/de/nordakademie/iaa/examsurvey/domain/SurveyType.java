package de.nordakademie.iaa.examsurvey.domain;


public enum SurveyType {

    /**
     * Applicable for a {@link Survey} which aims to commit on a day
     */
    DATE,

    /**
     * Applicable for a {@link Survey} which aims to commit on a time
     */
    TIME,

    /**
     * Applicable for a {@link Survey} which aims to commit on a range of time
     */
    TIME_RANGE,

    /**
     * Applicable for a {@link Survey} which aims to commit on a range of days
     */
    DATE_RANGE,
}
