package com.github.mszarlinski.stories.account.ui;

import com.github.mszarlinski.stories.account.AccountModuleFacade;
import com.github.mszarlinski.stories.account.FindOrCreateAccountCommand;
import com.github.mszarlinski.stories.account.UserDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SignInController {

    private final AccountModuleFacade accountModuleFacade;

    SignInController(AccountModuleFacade accountModuleFacade) {
        this.accountModuleFacade = accountModuleFacade;
    }

    /**
     * @param jwt - token containing claims confirmed with authorization server (process of token validation is performed by Spring Security)
     * @see org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
     */
    @PostMapping("/signin")
    SignInResponse signIn(@AuthenticationPrincipal Jwt jwt) { //TODO: custom UserDetails class
        UserDto user = accountModuleFacade.findOrCreate(
                new FindOrCreateAccountCommand(
                        jwt.getClaimAsString("given_name"),
                        jwt.getClaimAsString("family_name"),
                        jwt.getClaimAsString("email"),
                        jwt.getClaimAsString("picture"))
        );

        return new SignInResponse(user.getName(), user.getLastName(), user.getPictureUrl());
    }
}

