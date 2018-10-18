package de.nordakademie.iaa.examsurvey.persistence;

import de.nordakademie.iaa.examsurvey.domain.Survey;
import de.nordakademie.iaa.examsurvey.domain.SurveyStatus;
import de.nordakademie.iaa.examsurvey.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends CrudRepository<Survey, Long>, JpaSpecificationExecutor<Survey> {
    List<Survey> findAllByInitiatorEqualsAndSurveyStatusEqualsOrSurveyStatusIn
            (final User initiator, final SurveyStatus privateStatus, final List<SurveyStatus> statuses);

    Optional<Survey> findByTitle(String title);
}
