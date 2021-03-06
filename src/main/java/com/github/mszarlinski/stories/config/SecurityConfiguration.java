package com.github.mszarlinski.stories.config;

import com.github.mszarlinski.stories.auth.AuthenticationModuleFacade;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    final AuthenticationModuleFacade authenticationModuleFacade;

    SecurityConfiguration(AuthenticationModuleFacade authenticationModuleFacade) {
        this.authenticationModuleFacade = authenticationModuleFacade;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/public/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .oauth2ResourceServer().jwt();
    }
}
