package de.nordakademie.iaa.examsurvey.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
public class User_ {

    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, Boolean> isAccountNonLocked;
    public static volatile SingularAttribute<User, Boolean> isAccountNonExpired;
    public static volatile SingularAttribute<User, Boolean> isCredentialsNonExpired;
    public static volatile SingularAttribute<User, Boolean> isEnabled;
}
