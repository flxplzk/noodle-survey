package de.nordakademie.iaa.examsurvey.persistence;

import de.nordakademie.iaa.examsurvey.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository for {@link User}.
 *
 * @author Felix Plazek
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
