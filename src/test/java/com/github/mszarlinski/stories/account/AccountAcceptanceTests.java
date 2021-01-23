package com.github.mszarlinski.stories.account;

import com.github.mszarlinski.stories.AcceptanceTests;
import com.github.mszarlinski.stories.account.ui.SignInResponse;
import com.github.mszarlinski.stories.test.SecurityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.github.mszarlinski.stories.test.SecurityUtils.authorized;
import static com.github.mszarlinski.stories.test.builder.TestUser.user;
import static org.assertj.core.api.Assertions.assertThat;

class AccountAcceptanceTests extends AcceptanceTests {

    @Autowired
    AccountModuleFacade accountModuleFacade;

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
                .hasFieldOrPropertyWithValue("lastName", user.getLastName());

        // when
        Optional<UserDto> savedUser = accountModuleFacade.findAccountByEmail(user.getEmail());

        // then
        assertThat(savedUser).hasValueSatisfying(u -> {
            assertThat(u.getName()).isEqualTo(user.getName());
            assertThat(u.getLastName()).isEqualTo(user.getLastName());
        });
    }
}
