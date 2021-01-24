package com.github.mszarlinski.stories.account.domain;

import org.springframework.data.annotation.Id;

import java.time.Instant;

public class Account {
    @Id
    private final String id;
    private final String name;
    private final String lastName;
    private final String email;
    private final String pictureUrl;
    private final Instant registrationDate;

    public Account(String id, String name, String lastName, String email, String pictureUrl, Instant registrationDate) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.registrationDate = registrationDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    String getEmail() {
        return email;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
