package de.nordakademie.iaa.examsurvey.service;

import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.UserAlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
public interface UserService extends UserDetailsService {

    /**
     * Creates new User. username must be unused.
     *
     * @param user to safe
     * @return created user
     * @throws UserAlreadyExistsException if a User with the username already exists
     */
    User createUser(User user);
}
