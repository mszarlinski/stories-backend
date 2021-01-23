package com.github.mszarlinski.stories.test.builder;

import java.util.UUID;

public class TestUser {
    private final String email;
    private final String name;
    private final String lastName;

    private TestUser(String email, String name, String lastName) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public static Builder user() {
        return new Builder();
    }

    public static class Builder {
        private String email = UUID.randomUUID().toString();
        private String name = "Scott";
        private String lastName = "Tiger";

        public TestUser build() {
            return new TestUser(email, name, lastName);
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }
    }
}
