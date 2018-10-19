package de.nordakademie.iaa.examsurvey.controller;

import com.google.common.collect.Lists;
import de.nordakademie.iaa.examsurvey.domain.Notification;
import de.nordakademie.iaa.examsurvey.domain.User;
import de.nordakademie.iaa.examsurvey.service.AuthenticationService;
import de.nordakademie.iaa.examsurvey.service.NotificationService;
import de.nordakademie.iaa.examsurvey.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.security.Principal;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController controllerUnderTest;
    private UserService userService;
    private AuthenticationService authenticationService;
    private NotificationService notificationService;

    @Before
    public void setUp() throws Exception {
        userService = mock(UserService.class);
        authenticationService = mock(AuthenticationService.class);
        notificationService = mock(NotificationService.class);
        controllerUnderTest = new UserController(userService, authenticationService, notificationService);
    }

    @Test
    public void user() {
        // GIVEN
        Principal user = mock(Principal.class);

        // WHEN
        Principal principal = controllerUnderTest.user(user);

        // THEN
        assertThat(principal, is(user));
    }

    @Test
    public void createUser() {
        // GIVEN
        User user = mock(User.class);
        User returnedUser = mock(User.class);

        when(userService.createUser(user)).thenReturn(returnedUser);

        // WHEN
        User createdUser = controllerUnderTest.createUser(user);

        // THEN
        assertThat(createdUser, is(returnedUser));

    }

    @Test
    public void getNotifications() {
        // GIVEN
        User user = mock(User.class);
        List<Notification> returnNotifications = Lists.newArrayList(mock(Notification.class), mock(Notification.class));

        when(authenticationService.getCurrentAuthenticatedUser()).thenReturn(user);
        when(notificationService.getNotificationsForUser(user)).thenReturn(returnNotifications);

        // WHEN
        List<Notification> notifications = controllerUnderTest.getNotifications();

        // THEN
        assertThat(notifications, is(returnNotifications));
        verify(authenticationService, times(1)).getCurrentAuthenticatedUser();
    }
}