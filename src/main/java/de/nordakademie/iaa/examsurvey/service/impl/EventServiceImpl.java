package de.nordakademie.iaa.examsurvey.service.impl;

import de.nordakademie.iaa.examsurvey.domain.Event;
import de.nordakademie.iaa.examsurvey.domain.NotificationType;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.persistence.EventRepository;
import de.nordakademie.iaa.examsurvey.service.EventService;
import de.nordakademie.iaa.examsurvey.service.NotificationService;
import de.nordakademie.iaa.examsurvey.service.SurveyService;

public class EventServiceImpl implements EventService {
    private final SurveyService surveyService;
    private final EventRepository eventRepository;
    private final NotificationService notificationService;

    public EventServiceImpl(SurveyService surveyService, EventRepository eventRespository, NotificationService notificationService) {
        this.surveyService = surveyService;
        this.eventRepository = eventRespository;
        this.notificationService = notificationService;
    }

    @Override
    public Event createEvent(Event event, User authenticatedUser) {
        surveyService.closeSurvey(event.getSurvey(), authenticatedUser);
        notificationService.notifyUsersWithNotificationType(NotificationType.EVENT_PLANNED, event.getSurvey());
        return eventRepository.save(event);
    }
}
