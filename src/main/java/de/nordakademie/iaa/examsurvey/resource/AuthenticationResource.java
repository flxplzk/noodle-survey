package de.nordakademie.iaa.examsurvey.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthenticationResource {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
