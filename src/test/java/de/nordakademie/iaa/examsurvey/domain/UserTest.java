package de.nordakademie.iaa.examsurvey.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void equalsAndHashcode() {
        EqualsVerifier.forClass(User.class)
                .withRedefinedSuperclass()
                .usingGetClass()
                .verify();
    }

}