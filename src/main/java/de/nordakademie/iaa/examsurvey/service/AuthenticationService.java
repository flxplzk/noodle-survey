package de.nordakademie.iaa.examsurvey.service;

import de.nordakademie.iaa.examsurvey.domain.User;

public interface AuthenticationService {

    User getCurrentAuthenticatedUser();
}
