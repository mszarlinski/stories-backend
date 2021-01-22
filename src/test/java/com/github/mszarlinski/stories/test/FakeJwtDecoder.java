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
        mockUser(SecurityUtils.MOCK_USER, SecurityUtils.MOCK_TOKEN);
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        String user = Optional.ofNullable(tokenToUser.get(token))
                .orElseThrow(() -> new IllegalStateException("JWT not initialized"));

        return Jwt.withTokenValue("fake")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(10000))
                .subject(user)
                .header("typ", "JWT")
                .build();
    }

    public void clear() {
        tokenToUser.clear();
    }
}
