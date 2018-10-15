package de.nordakademie.iaa.examsurvey.configuration;

import de.nordakademie.iaa.examsurvey.secrurity.SurveyBasicAuthenticationEntryPoint;
import de.nordakademie.iaa.examsurvey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final String REALM = "SURVEY_REALM";

    @Autowired
    private UserService userService;

    @Bean
    public BasicAuthenticationEntryPoint basicAuthenticationEntryPoint() {
        return new SurveyBasicAuthenticationEntryPoint(REALM);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/registration").permitAll()
                .antMatchers(HttpMethod.GET, "/index.html", "/", "/css/**", "/js/**",
                        "/bower_components/**").permitAll()
                .anyRequest().authenticated()

                .and().userDetailsService(userService)
                .csrf().disable();
        ;
    }
}
