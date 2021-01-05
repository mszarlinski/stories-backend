package com.github.mszarlinski.stories.common;

import org.springframework.util.Assert;

import java.util.Objects;

public class StoryId {
    private final String value;

    private StoryId(String value) {
        this.value = value;
    }

    public static StoryId of(String value) {
        Assert.notNull(value, "value cannot be null");
        return new StoryId(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryId storyId = (StoryId) o;
        return value.equals(storyId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
