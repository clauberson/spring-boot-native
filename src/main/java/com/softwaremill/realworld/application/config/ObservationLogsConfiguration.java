package com.softwaremill.realworld.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationTextPublisher;

@Configuration
public class ObservationLogsConfiguration {

    private final Logger logger = LoggerFactory.getLogger(ObservationLogsConfiguration.class);

    @Bean
    public ObservationTextPublisher observationTextPublisher() {
        return new ObservationTextPublisher(
                logger::info,
                context -> context.getLowCardinalityKeyValues().stream()
                        .anyMatch(keyValue -> keyValue.getKey().equals("context")
                                && keyValue.getValue().equals("user")),
                Observation.Context::getName);
    }
}
