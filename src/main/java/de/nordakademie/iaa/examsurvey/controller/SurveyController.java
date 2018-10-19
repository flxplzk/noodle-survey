package de.nordakademie.iaa.examsurvey.controller;

import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.PermissionDeniedException;
import de.nordakademie.iaa.examsurvey.exception.SurveyAlreadyExistsException;
import de.nordakademie.iaa.examsurvey.exception.SurveyNotFoundException;
import de.nordakademie.iaa.examsurvey.service.AuthenticationService;
import de.nordakademie.iaa.examsurvey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Felix Plazek
 * @author Robert Peters
 */
@RestController
public class SurveyController {
    private final SurveyService surveyService;
    private final AuthenticationService authenticationService;

    @Autowired
    public SurveyController(SurveyService surveyService, AuthenticationService authenticationService) {
        this.surveyService = surveyService;
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/surveys",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ExceptionHandler({PermissionDeniedException.class, SurveyAlreadyExistsException.class})
    public Survey createSurvey(@RequestBody Survey survey) {
        User authenticatedUser = authenticationService.getCurrentAuthenticatedUser();
        return surveyService.createSurvey(survey, authenticatedUser);
    }

    @RequestMapping(value = "/surveys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Survey> loadSurveys() {
        User authenticatedUser = authenticationService.getCurrentAuthenticatedUser();
        return surveyService.loadAllSurveysWithUser(authenticatedUser);
    }

    @RequestMapping(value = "/surveys/{identifier}/options",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Option> saveOptions(@PathVariable String identifier, @RequestBody List<Option> options) {
        User authenticatedUser = authenticationService.getCurrentAuthenticatedUser();
        return surveyService.saveOptionsForSurvey(options, identifier, authenticatedUser);
    }

    @RequestMapping(value = "/surveys/{identifier}/options",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ExceptionHandler({SurveyNotFoundException.class})
    public List<Option> loadOptions(@PathVariable String identifier) {
        User authenticatedUser = authenticationService.getCurrentAuthenticatedUser();
        return surveyService.loadAllOptionsForSurveyWithUser(identifier, authenticatedUser);
    }

    @RequestMapping(value = "/surveys/{identifier}/participations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ExceptionHandler(value = SurveyNotFoundException.class)
    public List<Participation> loadParticipations(@PathVariable(name = "identifier") String identifier) {
        User authenticatedUser = authenticationService.getCurrentAuthenticatedUser();
        return surveyService.loadAllParticipationsForSurveyWithUser(identifier, authenticatedUser);
    }
}
