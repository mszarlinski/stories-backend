package com.github.mszarlinski.stories.auth;

import org.springframework.util.Assert;

public class FindOrCreateUserCommand {
    private final String userId;
    private final String name;
    private final String lastName;
    private final String email;
    private final String pictureUrl;

    public FindOrCreateUserCommand(String userId, String name, String lastName, String email, String pictureUrl) {
        Assert.notNull(name, "userId cannot be null");
        Assert.notNull(name, "name cannot be null");
        Assert.notNull(lastName, "lastName cannot be null");
        Assert.notNull(email, "email cannot be null");
        Assert.notNull(pictureUrl, "pictureUrl cannot be null");

        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.pictureUrl = pictureUrl;
    }

    public String getUserId() {
        return userId;
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
}
