package de.nordakademie.iaa.examsurvey.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(Option.class)
public class Option_ extends AuditModel_{
    protected Option_() {
    }
    public static volatile SingularAttribute<Option, Date> dateTime;
    public static volatile SingularAttribute<Option, Survey> survey;
}
