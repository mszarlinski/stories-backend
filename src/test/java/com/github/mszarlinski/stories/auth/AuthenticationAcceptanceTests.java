package com.github.mszarlinski.stories.auth;

import com.github.mszarlinski.stories.AcceptanceTests;
import com.github.mszarlinski.stories.auth.ui.SignInResponse;
import com.github.mszarlinski.stories.test.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.github.mszarlinski.stories.test.SecurityUtils.authorized;
import static com.github.mszarlinski.stories.test.builder.TestUser.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class AuthenticationAcceptanceTests extends AcceptanceTests {

    @Autowired
    AuthenticationModuleFacade authenticationModuleFacade;

    @Test
    void shouldCreateAccountIfNotExistOnSignIn() {
        // given
        var user = user().build();
        fakeJwtDecoder.mockUser(user, SecurityUtils.MOCK_TOKEN);

        // when
        var signInResponse = client.postForObject("/signin", authorized(), SignInResponse.class);

        // then
        assertThat(signInResponse)
                .hasFieldOrPropertyWithValue("name", user.getName())
                .hasFieldOrPropertyWithValue("lastName", user.getLastName())
                .hasFieldOrPropertyWithValue("pictureUrl", user.getPictureUrl());

        // when
        Optional<UserDto> savedUser = authenticationModuleFacade.findUserById(user.getId());

        // then
        assertThat(savedUser).hasValueSatisfying(u -> {
            assertThat(u.getName()).isEqualTo(user.getName());
            assertThat(u.getLastName()).isEqualTo(user.getLastName());
        });
    }

    @Test
    void shouldReturn401WhenTokenIsNotProvided() {
        // when
        var signInResponse = client.postForEntity("/signin", null, SignInResponse.class);

        // then
        assertThat(signInResponse.getStatusCode()).isEqualTo(UNAUTHORIZED);
    }
}
