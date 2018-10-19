package de.nordakademie.iaa.examsurvey.service.impl;

import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.UserAlreadyExistsException;
import de.nordakademie.iaa.examsurvey.persistence.UserRepository;
import de.nordakademie.iaa.examsurvey.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static de.nordakademie.iaa.examsurvey.persistence.specification.UserSpecifications.hasUsername;

/**
 * UserService implementation.
 *
 * @author Felix Plazek
 */
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {
        findUserByUsername(user.getUsername()).orElseThrow(UserAlreadyExistsException::new);

        final String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No User with given username found"));
    }

    private Optional<User> findUserByUsername(final String userName) {
        return userRepository.findOne(hasUsername(userName));
    }
}
