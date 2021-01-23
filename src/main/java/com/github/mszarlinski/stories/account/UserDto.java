package com.github.mszarlinski.stories.account;

import com.github.mszarlinski.stories.account.domain.Account;

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

    static UserDto fromAccount(Account account) {
        return new UserDto(account.getName(), account.getLastName());
    }
}
