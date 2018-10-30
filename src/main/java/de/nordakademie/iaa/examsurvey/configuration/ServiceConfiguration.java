package de.nordakademie.iaa.examsurvey.configuration;

import de.nordakademie.iaa.examsurvey.persistence.EventRepository;
import de.nordakademie.iaa.examsurvey.persistence.NotificationRepository;
import de.nordakademie.iaa.examsurvey.persistence.OptionRepository;
import de.nordakademie.iaa.examsurvey.persistence.ParticipationRepository;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.persistence.UserRepository;
import de.nordakademie.iaa.examsurvey.service.AuthenticationService;
import de.nordakademie.iaa.examsurvey.service.EventService;
import de.nordakademie.iaa.examsurvey.service.NotificationService;
import de.nordakademie.iaa.examsurvey.service.OptionService;
import de.nordakademie.iaa.examsurvey.service.ParticipationService;
import de.nordakademie.iaa.examsurvey.service.SurveyService;
import de.nordakademie.iaa.examsurvey.service.UserService;
import de.nordakademie.iaa.examsurvey.service.impl.AuthenticationServiceImpl;
import de.nordakademie.iaa.examsurvey.service.impl.EventServiceImpl;
import de.nordakademie.iaa.examsurvey.service.impl.NotificationServiceImpl;
import de.nordakademie.iaa.examsurvey.service.impl.OptionServiceImpl;
import de.nordakademie.iaa.examsurvey.service.impl.ParticipationServiceImpl;
import de.nordakademie.iaa.examsurvey.service.impl.SurveyServiceImpl;
import de.nordakademie.iaa.examsurvey.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfiguration {

    @Bean
    @Scope(value = "singleton")
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Scope(value = "singleton")
    public UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Bean
    @Scope(value = "singleton")
    public SurveyService surveyService(final SurveyRepository surveyRepository,
                                       final NotificationService notificationService,
                                       final OptionService optionService,
                                       final ParticipationService participationService) {
        return new SurveyServiceImpl(surveyRepository, notificationService, optionService, participationService);
    }

    @Bean
    @Scope(value = "singleton")
    public AuthenticationService authenticationService(UserService userService) {
        return new AuthenticationServiceImpl(userService);
    }

    @Bean
    @Scope(value = "singleton")
    public NotificationService notificationService(NotificationRepository notificationRepository) {
        return new NotificationServiceImpl(notificationRepository);
    }

    @Bean
    @Scope(value = "singleton")
    public OptionService optionService(final OptionRepository optionRepository,
                                       final SurveyRepository surveyRepository) {
        return new OptionServiceImpl(surveyRepository, optionRepository);
    }

    @Bean
    @Scope(value = "singleton")
    public ParticipationService participationService(final ParticipationRepository participationRepository,
                                                     final SurveyRepository surveyRepository) {
        return new ParticipationServiceImpl(surveyRepository, participationRepository);
    }

    @Bean
    @Scope(value = "singleton")
    public EventService eventService(SurveyService surveyService,
                                     EventRepository eventRespository,
                                     NotificationService notificationService) {
        return new EventServiceImpl(surveyService, eventRespository, notificationService);
    }
}
