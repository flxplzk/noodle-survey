package de.nordakademie.iaa.examsurvey.domain;

import de.nordakademie.iaa.examsurvey.domain.AuditModel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(AuditModel.class)
public class AuditModel_ {
    protected AuditModel_() {

    }
    public static volatile SingularAttribute<AuditModel, Long> id;
    public static volatile SingularAttribute<Date, Long> updatedAt;
    public static volatile SingularAttribute<Date, Long> createdAt;
}
