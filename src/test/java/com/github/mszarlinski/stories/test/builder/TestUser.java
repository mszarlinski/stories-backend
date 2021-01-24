package com.github.mszarlinski.stories.test.builder;

import com.github.mszarlinski.stories.account.UserDto;

import java.util.UUID;

public class TestUser {
    private final String id;
    private final String email;
    private final String name;
    private final String lastName;
    private final String pictureUrl;

    private TestUser(String id, String email, String name, String lastName, String pictureUrl) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.pictureUrl = pictureUrl;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getId() {
        return id;
    }

    public UserDto toUserDto() {
        return new UserDto(name, lastName, pictureUrl);
    }

    public static Builder user() {
        return new Builder();
    }

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String email = UUID.randomUUID().toString();
        private String name = "Scott";
        private String lastName = "Tiger";
        private String pictureUrl = "http://mypicture.jpg";

        public TestUser build() {
            return new TestUser(id, email, name, lastName, pictureUrl);
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }
    }
}
