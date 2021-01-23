package com.github.mszarlinski.stories.test;

import com.github.mszarlinski.stories.test.builder.TestUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import static com.github.mszarlinski.stories.test.builder.TestUser.user;

public class SecurityUtils {

    public static final String MOCK_TOKEN = "MOCK_TOKEN";
    public static final TestUser MOCK_USER = user().build();

    public static <T> HttpEntity<T> authorized(T body, String token) {
        return new HttpEntity<>(body, authorizationHeader(token));
    }

    public static <T> HttpEntity<T> authorized(String token) {
        return authorized(null, token);
    }

    public static <T> HttpEntity<T> authorized(T body) {
        return authorized(body, MOCK_TOKEN);
    }

    public static <T> HttpEntity<T> authorized() {
        return authorized(null, MOCK_TOKEN);
    }

    private static HttpHeaders authorizationHeader(String token) {
        return new HttpHeaders() {{
            add(AUTHORIZATION, "Bearer " + token);
        }};
    }
}
