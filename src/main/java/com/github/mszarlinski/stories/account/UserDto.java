package com.github.mszarlinski.stories.account;

public class UserDto {
    private final String name;
    private final String lastName;

    public UserDto(String name, String lastName) {
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
