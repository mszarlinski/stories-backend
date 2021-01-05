package com.github.mszarlinski.stories.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(CorsProperties.class)
class WebConfiguration {

    private final CorsProperties corsProperties;

    WebConfiguration(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOriginPatterns(corsProperties.getOrigin());
            }
        };
    }
}

@ConstructorBinding
@ConfigurationProperties("cors")
class CorsProperties {
    private final String origin;

    CorsProperties(String origin) {
        this.origin = origin;
    }

    String getOrigin() {
        return origin;
    }
}
