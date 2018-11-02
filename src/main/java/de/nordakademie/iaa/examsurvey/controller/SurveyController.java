package de.nordakademie.iaa.examsurvey.controller;

import com.google.common.collect.Sets;
import de.nordakademie.iaa.examsurvey.controller.filtercriterion.FilterCriterion;
import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Participation;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.service.AuthenticationService;
import de.nordakademie.iaa.examsurvey.service.OptionService;
import de.nordakademie.iaa.examsurvey.service.ParticipationService;
import de.nordakademie.iaa.examsurvey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    private final OptionService optionService;
    private final ParticipationService participationService;

    @Autowired
    public SurveyController(final SurveyService surveyService,
                            final AuthenticationService authenticationService,
                            final OptionService optionService,
                            final ParticipationService participationService) {
        this.surveyService = surveyService;
        this.authenticationService = authenticationService;
        this.optionService = optionService;
        this.participationService = participationService;
    }

    @RequestMapping(value = PATH_SURVEYS,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Survey createSurvey(@RequestBody Survey survey) {
        return surveyService.createSurvey(survey, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEYS + "/{" + PATH_V_IDENTIFIER + "}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Survey updateSurvey(@PathVariable(name = PATH_V_IDENTIFIER) Long id,
                               @RequestBody Survey survey) {
        survey.setId(id);
        return surveyService.update(survey, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEYS + "/{" + PATH_V_IDENTIFIER + "}",
            method = RequestMethod.DELETE)
    public void deleteSurvey(@PathVariable(name = PATH_V_IDENTIFIER) Long id) {
        surveyService.deleteSurvey(id, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEYS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Survey> loadSurveys(@RequestParam(name = "filter", required = false) final List<String> filterParams) {
        Set<FilterCriterion> filterCriteria = FilterCriterion.of(
                filterParams != null ? Sets.newHashSet(filterParams) : Sets.newHashSet());
        return surveyService.loadAllSurveysWithFilterCriteriaAndUser(filterCriteria, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEYS_IDENTIFIER,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Survey loadSurvey(@PathVariable(value = PATH_V_IDENTIFIER) final Long identifier) {
        return surveyService.loadSurveyWithUser(identifier, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEY_OPTIONS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Option> loadOptions(@PathVariable(value = PATH_V_IDENTIFIER) Long identifier) {
        return optionService.loadAllOptionsOfSurveyForUser(identifier, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEY_PARTICIPATIONS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Participation> loadParticipations(@PathVariable(name = PATH_V_IDENTIFIER) Long identifier) {
        return participationService.loadAllParticipationsOfSurveyForUser(identifier, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEY_PARTICIPATIONS,
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Participation createParticipationForSurvey(@RequestBody Participation participation,
                                                      @PathVariable(name = PATH_V_IDENTIFIER) Long surveyIdentifier) {
        return participationService.saveParticipationForSurveyWithAuthenticatedUser(participation, surveyIdentifier, getAuthenticatedUser());
    }

    @RequestMapping(value = PATH_SURVEY_PARTICIPATIONS + "/{participation}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Participation saveParticipationForSurvey(@RequestBody Participation participation,
                                                    @PathVariable(name = PATH_V_IDENTIFIER) Long surveyIdentifier,
                                                    @PathVariable(name = "participation") Long participationIdentifier) {
        participation.setId(participationIdentifier);
        return participationService.saveParticipationForSurveyWithAuthenticatedUser(participation, surveyIdentifier, getAuthenticatedUser());
    }

    private User getAuthenticatedUser() {
        return authenticationService.getCurrentAuthenticatedUser();
    }
}
