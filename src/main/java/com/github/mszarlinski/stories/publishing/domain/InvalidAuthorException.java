package com.github.mszarlinski.stories.publishing.domain;

public class InvalidAuthorException extends InvalidStoryException {
    public InvalidAuthorException() {
        super("Author information is required to publish a story");
    }
}