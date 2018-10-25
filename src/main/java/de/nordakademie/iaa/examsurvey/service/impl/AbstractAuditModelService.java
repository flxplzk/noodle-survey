package de.nordakademie.iaa.examsurvey.service.impl;

import de.nordakademie.iaa.examsurvey.domain.AuditModel;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.PermissionDeniedException;

abstract class AbstractAuditModelService<T extends AuditModel> {

    void requireNonNullUser(final User initiator) {
        if (initiator == null) {
            throw new PermissionDeniedException("initiator must be non null");
        }
    }
}
