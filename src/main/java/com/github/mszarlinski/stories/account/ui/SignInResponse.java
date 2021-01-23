package com.github.mszarlinski.stories.account.ui;

public class SignInResponse {
    private final String name;
    private final String lastName;

    SignInResponse(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}
