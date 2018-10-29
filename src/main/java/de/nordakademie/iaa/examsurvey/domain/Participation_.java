package de.nordakademie.iaa.examsurvey.domain;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Set;

@StaticMetamodel(Participation.class)
public class Participation_ extends AuditModel_ {
    protected Participation_() {
    }

    public static volatile SingularAttribute<Participation, User> user;
    public static volatile SingularAttribute<Participation, Survey> survey;
    public static volatile PluralAttribute<Participation, Set, Option> options;
}
