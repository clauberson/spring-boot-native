package com.softwaremill.realworld.application.user.config;

import com.softwaremill.realworld.application.config.BearerTokenSupplier;
import com.softwaremill.realworld.application.user.service.ObservedUserService;
import com.softwaremill.realworld.application.user.service.UserApplicationService;
import com.softwaremill.realworld.application.user.service.UserService;
import com.softwaremill.realworld.domain.user.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.micrometer.observation.ObservationRegistry;

@Configuration
public class UserServiceConfiguration {

    @Bean
    public UserService userService(
            UserApplicationService userApplicationService, ObservationRegistry observationRegistry) {
        return new ObservedUserService(userApplicationService, observationRegistry);
    }

    @Bean
    public UserApplicationService userApplicationService(
            UserRepository userRepository, PasswordEncoder passwordEncoder, BearerTokenSupplier bearerTokenSupplier) {
        return new UserApplicationService(userRepository, passwordEncoder, bearerTokenSupplier);
    }
}
