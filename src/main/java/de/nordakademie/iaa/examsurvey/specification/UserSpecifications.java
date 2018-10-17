package de.nordakademie.iaa.examsurvey.specification;

import de.nordakademie.iaa.examsurvey.domain.User;

import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import javax.persistence.metamodel.Type;
import java.lang.reflect.Member;

public class UserSpecifications {

    @StaticMetamodel(User.class)
    public static class User_ {
        public static volatile SingularAttribute<User, String> username;
    }
}
