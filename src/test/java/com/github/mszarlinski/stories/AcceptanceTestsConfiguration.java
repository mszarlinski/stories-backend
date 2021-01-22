package com.github.mszarlinski.stories;

import com.github.mszarlinski.stories.test.FakeJwtDecoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

@TestConfiguration
class AcceptanceTestsConfiguration {

    @Bean
    Clock clock() {
        return Clock.fixed(Instant.parse("2020-12-29T12:00:00.000Z"), ZoneOffset.UTC);
    }

    @Bean
    FakeJwtDecoder jwtDecoder() {
        return new FakeJwtDecoder();
    }
}

