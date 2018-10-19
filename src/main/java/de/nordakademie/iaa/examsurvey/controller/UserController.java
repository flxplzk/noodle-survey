package de.nordakademie.iaa.examsurvey.controller;

import de.nordakademie.iaa.examsurvey.domain.Notification;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.exception.UserAlreadyExistsException;
import de.nordakademie.iaa.examsurvey.service.AuthenticationService;
import de.nordakademie.iaa.examsurvey.service.NotificationService;
import de.nordakademie.iaa.examsurvey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author Felix Plazek
 */
@RestController
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final NotificationService notificationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService, NotificationService notificationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/authentication",
            method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(value = "/registration",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/users/me/notifications",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Notification> getNotifications(){
        User authenticatedUser = authenticationService.getCurrentAuthenticatedUser();
        return notificationService.getNotifications(authenticatedUser);
    }
}
