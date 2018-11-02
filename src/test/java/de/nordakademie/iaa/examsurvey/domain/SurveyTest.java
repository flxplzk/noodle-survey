package de.nordakademie.iaa.examsurvey.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.GregorianCalendar;

public class SurveyTest {

    @Test
    public void equalsAndHashcode() {
        final Option option1 = new Option();
        option1.setDateTime(
                new GregorianCalendar(2018, 11, 1, 12, 0).getTime()
        );

        final Option option2 = new Option();
        option2.setDateTime(
                new GregorianCalendar(2018, 12, 1, 12, 0).getTime()
        );

        EqualsVerifier.forClass(Survey.class)
                .withRedefinedSuperclass()
                .withPrefabValues(Option.class, option1, option2)
                .withIgnoredFields("options")
                .usingGetClass()
                .verify();
    }
}