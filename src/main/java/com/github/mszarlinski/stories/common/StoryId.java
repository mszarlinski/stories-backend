package com.github.mszarlinski.stories.common;

public record StoryId(String value) {

    public static StoryId of(String value) {
        return new StoryId(value);
    }
}
