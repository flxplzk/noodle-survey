package de.nordakademie.iaa.examsurvey.controller;

import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.service.AuthenticationService;
import de.nordakademie.iaa.examsurvey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
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
    public Survey createSurvey(@RequestBody Survey survey) {
        User authenticatedUser = authenticationService.getCurrentAuthenticatedUser();
        return surveyService.createSurvey(survey, authenticatedUser);
    }

    @RequestMapping(value = "/surveys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Survey> loadSurveys() {
        User authenticatedUser = authenticationService.getCurrentAuthenticatedUser();
        return surveyService.loadAllSurveysForUser(authenticatedUser);
    }

}
