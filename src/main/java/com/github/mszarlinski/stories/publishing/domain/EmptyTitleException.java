package com.github.mszarlinski.stories.publishing.domain;

public class EmptyTitleException extends InvalidStoryException {
    public EmptyTitleException() {
        super("Story title cannot be empty or blank");
    }
}