package com.github.mszarlinski.stories.auth.ui;

import com.github.mszarlinski.stories.auth.AuthenticationModuleFacade;
import com.github.mszarlinski.stories.auth.FindOrCreateUserCommand;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SignInController {

    private final AuthenticationModuleFacade authenticationModuleFacade;

    SignInController(AuthenticationModuleFacade authenticationModuleFacade) {
        this.authenticationModuleFacade = authenticationModuleFacade;
    }

    /**
     * @param jwt - token containing claims confirmed with authorization server (process of token validation is performed by Spring Security)
     * @see org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
     */
    @PostMapping("/signin")
    SignInResponse signIn(@AuthenticationPrincipal Jwt jwt) {
        var user = authenticationModuleFacade.findOrCreate(
                new FindOrCreateUserCommand(
                        jwt.getSubject(), // This relies heavily on external ID of user account created in Google
                        jwt.getClaimAsString("given_name"),
                        jwt.getClaimAsString("family_name"),
                        jwt.getClaimAsString("email"),
                        jwt.getClaimAsString("picture"))
        );

        return new SignInResponse(user.getName(), user.getLastName(), user.getPictureUrl());
    }
}

