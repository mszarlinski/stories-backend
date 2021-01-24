package com.github.mszarlinski.stories.account;

import com.github.mszarlinski.stories.account.domain.Account;

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

    static UserDto fromAccount(Account account) {
        return new UserDto(account.getName(), account.getLastName(), account.getPictureUrl());
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
