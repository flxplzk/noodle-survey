package de.nordakademie.iaa.examsurvey.integration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.SurveyStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIn.isIn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SurveyIntegrationTest {
    private static final Type TYPE_LIST_OF_SURVEYS = new TypeToken<List<Survey>>() {
    }.getType();
    private static final String SURVEYS_API_PATH = "/surveys";
    private static final String SURVEYS_OPTIONS_API_PATH = "/surveys/%s/options";
    private static final String SURVEYS_PARTICIPATIONS_API_PATH = "/surveys/%s/participations";
    private static final String USER_FELIX = "felix@plazek.de";
    private static final String USER_STEFAN = "stefan@reichert.de";
    private static final SurveyStatus[] ALLOWED_FOR_INITIATOR = SurveyStatus.values();
    private static final SurveyStatus[] ALLOWED_FOR_NON_INITIATOR = {SurveyStatus.CLOSED, SurveyStatus.OPEN};
    private static final long SURVEY_FELIX_OPEN = -9;
    private static final long SURVEY_FELIX_CLOSED = -8;
    private static final long SURVEY_FELIX_PRIVATE = -14;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void loadAllSurveys_withoutAuthorisation() throws Exception {
        mockMvc.perform(get(SURVEYS_API_PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(USER_FELIX)
    public void loadAllSurveys_withAuthorisation() throws Exception {
        mockMvc.perform(get(SURVEYS_API_PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_STEFAN)
    public void loadAllSurveys_withAuthorisationWithanotherUser() throws Exception {
        mockMvc.perform(get(SURVEYS_API_PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    @WithMockUser(USER_STEFAN)
    public void loadAllSurveys_withAuthorisationWithValidResponseData() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(SURVEYS_API_PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        List<Survey> surveys = convertResponseToListOfSurveys(mvcResult);
        requireAllowedSurveys(surveys, USER_STEFAN);
    }

    @Test
    @WithMockUser(USER_FELIX)
    public void loadAllSurveys_withAuthorisationWithValidResponseDataAnotherUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(SURVEYS_API_PATH)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        List<Survey> surveys = convertResponseToListOfSurveys(mvcResult);
        requireAllowedSurveys(surveys, USER_FELIX);
    }

    @Test
    @WithMockUser(USER_FELIX)
    public void loadAllOptions_forPrivateSurveyWithInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_OPTIONS_API_PATH, SURVEY_FELIX_PRIVATE))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_STEFAN)
    public void loadAllOptions_forPrivateSurveyWithNonInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_OPTIONS_API_PATH, SURVEY_FELIX_PRIVATE))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_STEFAN)
    public void loadAllOptions_forOpenSurveyWithNonInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_OPTIONS_API_PATH, SURVEY_FELIX_OPEN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_FELIX)
    public void loadAllOptions_forOpenSurveyWithInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_OPTIONS_API_PATH, SURVEY_FELIX_OPEN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_FELIX)
    public void loadAllOptions_forClosedSurveyWithInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_OPTIONS_API_PATH, SURVEY_FELIX_CLOSED))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_STEFAN)
    public void loadAllOptions_forClosedSurveyWithNonInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_OPTIONS_API_PATH, SURVEY_FELIX_OPEN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_FELIX)
    public void loadAllParticipations_forPrivateSurveyWithInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_PARTICIPATIONS_API_PATH, SURVEY_FELIX_PRIVATE))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_STEFAN)
    public void loadAllParticipations_forPrivateSurveyWithNonInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_PARTICIPATIONS_API_PATH, SURVEY_FELIX_PRIVATE))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_STEFAN)
    public void loadAllParticipations_forOpenSurveyWithNonInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_PARTICIPATIONS_API_PATH, SURVEY_FELIX_OPEN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_FELIX)
    public void loadAllParticipations_forOpenSurveyWithInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_PARTICIPATIONS_API_PATH, SURVEY_FELIX_OPEN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_FELIX)
    public void loadAllParticipations_forClosedSurveyWithInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_PARTICIPATIONS_API_PATH, SURVEY_FELIX_OPEN))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(USER_STEFAN)
    public void loadAllParticipations_forClosedSurveyWithNonInitiator() throws Exception {
        mockMvc.perform(get(String.format(SURVEYS_PARTICIPATIONS_API_PATH, SURVEY_FELIX_CLOSED))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
    }

    private void requireAllowedSurveys(List<Survey> surveys, String user) {
        for (Survey survey : surveys) {
            if (survey.getInitiator().getUsername().equals(user)) {
                assertThat(survey.getSurveyStatus(), isIn(ALLOWED_FOR_INITIATOR));
            } else {
                assertThat(survey.getSurveyStatus(), isIn(ALLOWED_FOR_NON_INITIATOR));
            }
        }
    }

    private List<Survey> convertResponseToListOfSurveys(MvcResult mvcResult) throws UnsupportedEncodingException {
        String jsonString = mvcResult.getResponse().getContentAsString();
        return new Gson().fromJson(jsonString, TYPE_LIST_OF_SURVEYS);
    }
}
