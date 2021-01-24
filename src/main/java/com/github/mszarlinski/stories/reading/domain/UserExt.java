package com.github.mszarlinski.stories.reading.domain;

import com.github.mszarlinski.stories.auth.UserDto;

public final class UserExt {
    public static String fullName(UserDto user) {
        return String.format("%s %s", user.getName(), user.getLastName());
    }
}
