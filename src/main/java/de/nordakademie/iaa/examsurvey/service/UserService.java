package de.nordakademie.iaa.examsurvey.service;

import de.nordakademie.iaa.examsurvey.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(User user);
}
