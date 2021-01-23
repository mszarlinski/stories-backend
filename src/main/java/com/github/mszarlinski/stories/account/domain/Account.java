package com.github.mszarlinski.stories.account.domain;

import org.springframework.data.annotation.Id;

public class Account {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;

    public Account(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
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
}
