package com.github.mszarlinski.stories.publishing.domain;

public class EmptyContentException extends InvalidStoryException {
    public EmptyContentException() {
        super("Story content cannot be empty or blank");
    }
}