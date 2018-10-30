package de.nordakademie.iaa.examsurvey.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author felix plazek
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SurveyNotFoundException extends SurveyApplicationException{
}
