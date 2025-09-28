package com.github.mszarlinski.stories.publishing.domain;

public class InvalidStoryException extends RuntimeException {
    public InvalidStoryException(String message) {
        super(message);
    }
}