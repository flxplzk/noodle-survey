package de.nordakademie.iaa.examsurvey.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Option.class)
public class Option_ {
    public static volatile SingularAttribute<Option, Long> id;
    public static volatile SingularAttribute<Option, LocalDateTime> fromDatetime;
    public static volatile SingularAttribute<Option, LocalDateTime> toDateTime;
    public static volatile SingularAttribute<Option, Survey> survey;
}
