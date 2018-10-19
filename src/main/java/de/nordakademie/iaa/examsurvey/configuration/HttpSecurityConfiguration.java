package de.nordakademie.iaa.examsurvey.configuration;

import de.nordakademie.iaa.examsurvey.secrurity.SurveyBasicAuthenticationEntryPoint;
import de.nordakademie.iaa.examsurvey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String REALM = "SURVEY_REALM";

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public HttpSecurityConfiguration(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public BasicAuthenticationEntryPoint basicAuthenticationEntryPoint() {
        return new SurveyBasicAuthenticationEntryPoint(REALM);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // using custum Entrypoint for disabling browser credentials dialog
                .httpBasic()
                .authenticationEntryPoint(basicAuthenticationEntryPoint())

                // permitting required resources
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/registration").permitAll()
                .antMatchers(HttpMethod.GET, "/index.html", "/", "/css/**", "/js/**",
                        "/node_modules/**").permitAll()
                .anyRequest().authenticated()

                // setting custom user details service
                .and().userDetailsService(userService)
                .csrf().disable();
    }
}
