package de.nordakademie.iaa.examsurvey.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

public class SurveyTest {

    @Test
    public void equalsAndHashcode() {
        Survey first = new Survey();
        first.setTitle("lalala");
        Survey seccond = new Survey();
        seccond.setTitle("lololo");
        Event firstEvent = new Event();
        firstEvent.setTitle("lalalalala");
        Event black = new Event();
        black.setTitle("lalalalalallalala");

        EqualsVerifier.forClass(Survey.class)
                .withRedefinedSuperclass()
                .withPrefabValues(Survey.class, first, seccond)
                .withPrefabValues(Event.class, firstEvent, black)
                .usingGetClass()
                .verify();
    }
}