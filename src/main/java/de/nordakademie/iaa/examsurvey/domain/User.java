package de.nordakademie.iaa.examsurvey.domain;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.Collections;

/**
 * Base Entity for application user.
 * Implementing the {@link UserDetails}-Interface for Spring security integration.
 *
 * @author Felix Plazek
 */
@Entity
public class User implements UserDetails {
    private Long id;
    private String password;
    private String username;
    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public User() {
        isAccountNonLocked = true;
        isAccountNonExpired = true;
        isCredentialsNonExpired = true;
        isEnabled = true;
    }

    public User(String username, String password) {
        this();
        this.password = password;
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // not needed so far implementation may be useful in future stories
    }

    @Column(nullable = false)
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NaturalId
    @Column(nullable = false)
    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Column(nullable = false)
    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    @Column(nullable = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @Column(nullable = false)
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
