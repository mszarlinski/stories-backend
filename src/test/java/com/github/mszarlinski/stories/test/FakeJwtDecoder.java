package com.github.mszarlinski.stories.test;

import com.github.mszarlinski.stories.test.builder.TestUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//TODO: integration tests with WireMock mocking authorization server
public class FakeJwtDecoder implements JwtDecoder {

    private final Map<String, TestUser> tokenToUser = new HashMap<>();

    public void mockUser(TestUser user, String token) {
        this.tokenToUser.put(token, user);
    }

    public void mockUser() {
        mockUser(SecurityUtils.MOCK_USER, SecurityUtils.MOCK_TOKEN);
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        TestUser user = Optional.ofNullable(tokenToUser.get(token))
                .orElseThrow(() -> new IllegalStateException("JWT not initialized"));

        return Jwt.withTokenValue("fake")
                .subject(user.getId()) //TODO: is this a correct assumption?
                .claim("given_name", user.getName())
                .claim("family_name", user.getLastName())
                .claim("email", user.getEmail())
                .claim("picture", user.getPictureUrl())
                .header("typ", "JWT")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(10000))
                .build();
    }

    public void clear() {
        tokenToUser.clear();
    }
}
