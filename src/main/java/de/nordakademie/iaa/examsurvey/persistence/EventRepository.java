package de.nordakademie.iaa.examsurvey.persistence;

import de.nordakademie.iaa.examsurvey.domain.Event;
import de.nordakademie.iaa.examsurvey.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link Event}.
 *
 * @author Felix Plazek
 */
public interface EventRepository extends CrudRepository<Event, Long>, JpaSpecificationExecutor<Event> {
}
