package com.github.mszarlinski.stories.test;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeJwtDecoder implements JwtDecoder {

    private final Map<String, String> tokenToUser = new HashMap<>();

    public void mockUser(String user, String token) {
        this.tokenToUser.put(token, user);
    }

    public void mockUser() {
        mockUser(SecurityUtils.MOCK_TOKEN, SecurityUtils.MOCK_USER);
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        String user = Optional.ofNullable(tokenToUser.get(token))
                .orElseThrow(() -> new IllegalStateException("JWT not initialized"));

        return new Jwt(
                "fake",
                Instant.now(),
                Instant.now().plusSeconds(10000),
                Map.of("kid", "eea1b1f42807a8cc136a03a3c16d29db8296daf0", "typ", "JWT", "alg", "RS256"),
                Map.of("sub", user, "iss", "accounts.google.com")
        );
    }

    public void clear() {
        tokenToUser.clear();
    }
}
