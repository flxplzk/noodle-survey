package de.nordakademie.iaa.examsurvey.controller;

import de.nordakademie.iaa.examsurvey.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;

public class ParticipationController {
    private final ParticipationService participationService;

    @Autowired
    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }
}
