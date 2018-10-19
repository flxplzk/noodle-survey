package de.nordakademie.iaa.examsurvey.controller;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.service.AuthenticationService;
import de.nordakademie.iaa.examsurvey.service.SurveyService;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.NotNull;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SurveyControllerTest {
    private SurveyController controllerUnderTest;
    private SurveyService surveyService;
    private AuthenticationService authenticationService;

    @Before
    public void setUp() throws Exception {
        surveyService = mock(SurveyService.class);
        authenticationService = mock(AuthenticationService.class);
        controllerUnderTest = new SurveyController(surveyService, authenticationService);
    }

    @Test
    public void createSurvey() {
        // GIVEN
        Survey survey = mock(Survey.class);
        Survey anotherSurvey = mock(Survey.class);
        User user = mock(User.class);

        when(authenticationService.getCurrentAuthenticatedUser()).thenReturn(user);
        when(surveyService.createSurvey(survey, user)).thenReturn(anotherSurvey);

        // WHEN
        Survey createdSurvey = controllerUnderTest.createSurvey(survey);

        // THEN
        assertThat(createdSurvey, is(anotherSurvey));
        verify(authenticationService, times(1)).getCurrentAuthenticatedUser();

    }

    @Test
    public void loadSurveys() {
        // GIVEN
        List<Survey> surveys = Lists.newArrayList(mock(Survey.class),
                mock(Survey.class));
        User user = mock(User.class);

        when(authenticationService.getCurrentAuthenticatedUser()).thenReturn(user);
        when(surveyService.loadAllSurveysWithUser(user)).thenReturn(surveys);

        // WHEN
        List<Survey> loadedSurveys = controllerUnderTest.loadSurveys();

        // THEN
        assertThat(loadedSurveys, is(surveys));
        verify(authenticationService, times(1)).getCurrentAuthenticatedUser();
    }

    @Test
    public void saveOptions() {
        // GIVEN
        String identifier = "lol a random identifier mocking a survey title";
        List<Option> mockedOptions = Lists.newArrayList(mock(Option.class), mock(Option.class));
        User user = mock(User.class);

        when(authenticationService.getCurrentAuthenticatedUser()).thenReturn(user);
        when(surveyService.loadAllOptionsForSurveyWithUser(identifier, user)).thenReturn(mockedOptions);

        // WHEN
        List<Option> options = controllerUnderTest.loadOptions(identifier);

        // THEN
        assertThat(mockedOptions, is(options));
        verify(authenticationService, times(1)).getCurrentAuthenticatedUser();

    }

}