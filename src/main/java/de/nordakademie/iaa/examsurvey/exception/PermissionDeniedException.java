package de.nordakademie.iaa.examsurvey.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class PermissionDeniedException extends SurveyApplicationException {
    public PermissionDeniedException(String message) {
        super(message);
    }
}
