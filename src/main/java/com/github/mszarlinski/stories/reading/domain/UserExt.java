package com.github.mszarlinski.stories.reading.domain;

import com.github.mszarlinski.stories.account.UserDto;

public final class UserExt {
    public static String fullName(UserDto user) {
        return "%s %s".formatted(user.name(), user.lastName());
    }
}
