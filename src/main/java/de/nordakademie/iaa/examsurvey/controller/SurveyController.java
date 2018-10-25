package de.nordakademie.iaa.examsurvey.controller;

import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
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
    private static final String PATH_V_IDENTIFIER = "identifier";
    private static final String PATH_SURVEYS = "/surveys";
    private static final String PATH_SURVEY_PARTICIPATIONS = "/surveys/{identifier}/participations";
    private static final String PATH_SURVEY_OPTIONS = "/surveys/{identifier}/options";
    private static final String PATH_SURVEYS_IDENTIFIER = "/surveys/{identifier}";

    private final SurveyService surveyService;
    private final AuthenticationService authenticationService;

    @Autowired
    public SurveyController(SurveyService surveyService, AuthenticationService authenticationService) {
        this.surveyService = surveyService;
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = PATH_SURVEYS,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Survey createSurvey(@RequestBody Survey survey) {
        return surveyService.createSurvey(survey, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEYS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Survey> loadSurveys() {
        return surveyService.loadAllSurveysWithUser(getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEYS_IDENTIFIER,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Survey loadSurvey(@PathVariable(value = PATH_V_IDENTIFIER) final Long identifier) {
        return surveyService.loadSurveyWithUser(identifier, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEY_OPTIONS,
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Option> saveOptions(@PathVariable(value = PATH_V_IDENTIFIER) Long identifier,
                                    @RequestBody List<Option> options) {
        return surveyService.saveOptionsForSurvey(options, identifier, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEY_OPTIONS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Option> loadOptions(@PathVariable(value = PATH_V_IDENTIFIER) Long identifier) {
        return surveyService.loadAllOptionsOfSurveyForUser(identifier, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEY_PARTICIPATIONS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Participation> loadParticipations(@PathVariable(name = PATH_V_IDENTIFIER) Long identifier) {
        return surveyService.loadAllParticipationsOfSurveyForUser(identifier, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEY_PARTICIPATIONS,
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Participation saveParticipationForSurvey(@RequestBody Participation participation,
                                                    @PathVariable(name = PATH_V_IDENTIFIER) Long identifier) {
        return surveyService.saveParticipationForSurveyWithAuthenticatedUser(participation, identifier, getAuthenticatedUser());
    }

    private User getAuthenticatedUser() {
        return authenticationService.getCurrentAuthenticatedUser();
    }
}
