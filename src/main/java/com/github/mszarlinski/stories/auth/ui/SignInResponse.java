package com.github.mszarlinski.stories.auth.ui;

public class SignInResponse {
    private final String name;
    private final String lastName;
    private final String pictureUrl;

    SignInResponse(String name, String lastName, String pictureUrl) {
        this.name = name;
        this.lastName = lastName;
        this.pictureUrl = pictureUrl;
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
