package com.github.mszarlinski.stories.account;

import org.springframework.util.Assert;

public class FindOrCreateAccountCommand {
    private final String name;
    private final String lastName;
    private final String email;

    public FindOrCreateAccountCommand(String name, String lastName, String email) {
        Assert.notNull(name, "name cannot be null");
        Assert.notNull(lastName, "lastName cannot be null");
        Assert.notNull(email, "email cannot be null");

        this.name = name;
        this.lastName = lastName;
        this.email = email;
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
}
