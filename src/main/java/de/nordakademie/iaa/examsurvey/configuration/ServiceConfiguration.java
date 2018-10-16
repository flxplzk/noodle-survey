package de.nordakademie.iaa.examsurvey.configuration;

import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.persistence.SurveyRepository;
import de.nordakademie.iaa.examsurvey.persistence.UserRepository;
import de.nordakademie.iaa.examsurvey.service.SurveyService;
import de.nordakademie.iaa.examsurvey.service.UserService;
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
    public SurveyService surveyService(SurveyRepository surveyRepository) {
        return new SurveyServiceImpl(surveyRepository);
    }
}
