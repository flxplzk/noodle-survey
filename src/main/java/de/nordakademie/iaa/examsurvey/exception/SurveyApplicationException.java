package de.nordakademie.iaa.examsurvey.exception;

/**
 * ParentException for this application all {@link RuntimeException}'s will be thrown as type of this Exception
 *
 * @author Felix Plazek
 */
public class SurveyApplicationException extends RuntimeException {
    public SurveyApplicationException() {
    }

    public SurveyApplicationException(String message) {
        super(message);
    }

    public SurveyApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SurveyApplicationException(Throwable cause) {
        super(cause);
    }

    public SurveyApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
