package de.nordakademie.iaa.examsurvey.controller;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Option;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.service.AuthenticationService;
import de.nordakademie.iaa.examsurvey.service.OptionService;
import de.nordakademie.iaa.examsurvey.service.ParticipationService;
import de.nordakademie.iaa.examsurvey.service.SurveyService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SurveyControllerTest {
    private SurveyController controllerUnderTest;
    private SurveyService surveyService;
    private AuthenticationService authenticationService;
    private ParticipationService participationService;
    private OptionService optionService;

    @Before
    public void setUp() throws Exception {
        surveyService = mock(SurveyService.class);
        authenticationService = mock(AuthenticationService.class);
        participationService = mock(ParticipationService.class);
        optionService = mock(OptionService.class);
        controllerUnderTest = new SurveyController(surveyService, authenticationService, optionService, participationService);
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
    public void loadOptionsForSurvey() {
        // GIVEN
        Long id = -1L;
        List<Option> mockedOptions = Lists.newArrayList(mock(Option.class), mock(Option.class));
        User user = mock(User.class);

        when(authenticationService.getCurrentAuthenticatedUser()).thenReturn(user);
        when(optionService.loadAllOptionsOfSurveyForUser(id, user)).thenReturn(mockedOptions);

        // WHEN
        List<Option> options = controllerUnderTest.loadOptions(id);

        // THEN
        assertThat(mockedOptions, is(options));
        verify(authenticationService, times(1)).getCurrentAuthenticatedUser();

    }

    @Test
    public void updateSurvey() {
        //Given
        Long id = -1L;
        Survey mockedSurvey = mock(Survey.class);
        User user = mock(User.class);

        when(authenticationService.getCurrentAuthenticatedUser()).thenReturn(user);
        when(surveyService.update(mockedSurvey, user)).thenReturn(mockedSurvey);

        //WHEN
        Survey surveyReturn = controllerUnderTest.updateSurvey(id, mockedSurvey);

        //THEN
        assertThat(mockedSurvey, is(surveyReturn));
        verify(authenticationService, times(1)).getCurrentAuthenticatedUser();
    }

    @Test
    public void deleteSurvey() {
        //GIVEN
        Long id = -1L;
        User user = mock(User.class);

        when(authenticationService.getCurrentAuthenticatedUser()).thenReturn(user);

        //WHEN
        controllerUnderTest.deleteSurvey(id);

        //THEN
        verify(authenticationService, times(1)).getCurrentAuthenticatedUser();
        verify(surveyService, times(1)).deleteSurvey(id, user);
    }

    @Test
    public void loadSurveys1() {
        //GIVEN
        User user = mock(User.class);
        List<Survey> mockSurveys = Lists.newArrayList(mock(Survey.class), mock(Survey.class), mock(Survey.class));


        when(authenticationService.getCurrentAuthenticatedUser()).thenReturn(user);
        when(surveyService.loadAllSurveysWithUser(user)).thenReturn(mockSurveys);

        //WHEN
        List<Survey> surveys = controllerUnderTest.loadSurveys();

        //THEN
        verify(authenticationService, times(1)).getCurrentAuthenticatedUser();
        verify(surveyService, times(1)).loadAllSurveysWithUser(user);
        assertThat(mockSurveys, is(surveys));

    }

    @Test
    public void loadSurvey() {
        // TODO

    }

    @Test
    public void loadOptions() {
        // TODO

    }

    @Test
    public void loadParticipations() {
        // TODO

    }

    @Test
    public void createParticipationForSurvey() {
        // TODO

    }

    @Test
    public void saveParticipationForSurvey() {
        // TODO

    }
}