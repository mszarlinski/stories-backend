package com.github.mszarlinski.stories.auth;

import com.github.mszarlinski.stories.auth.domain.User;

public class UserDto {
    private final String name;
    private final String lastName;
    private final String pictureUrl;

    public UserDto(String name, String lastName, String pictureUrl) {
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

    static UserDto fromAccount(User user) {
        return new UserDto(user.getName(), user.getLastName(), user.getPictureUrl());
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
