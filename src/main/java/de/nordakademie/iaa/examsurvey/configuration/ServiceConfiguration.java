package de.nordakademie.iaa.examsurvey.configuration;

import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.persistence.UserRepository;
import de.nordakademie.iaa.examsurvey.service.UserService;
import de.nordakademie.iaa.examsurvey.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfiguration {

    @Bean
    @Scope(value = "singleton")
    public UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        UserService userService = new UserServiceImpl(userRepository);
        User user = new User("admin", passwordEncoder.encode("admin"));
        userService.save(user);
        return userService;
    }
}
