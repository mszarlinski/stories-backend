package com.github.mszarlinski.stories.config;

import com.github.mszarlinski.stories.auth.AuthenticationModuleFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
class SecurityConfiguration {

    final AuthenticationModuleFacade authenticationModuleFacade;

    SecurityConfiguration(AuthenticationModuleFacade authenticationModuleFacade) {
        this.authenticationModuleFacade = authenticationModuleFacade;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/public/**").permitAll()
                        .anyRequest().authenticated())
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))
                .build();
    }
}
