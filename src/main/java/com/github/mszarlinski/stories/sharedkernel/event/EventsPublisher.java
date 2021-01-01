package com.github.mszarlinski.stories.sharedkernel.event;

public interface EventsPublisher {
    void publish(DomainEvent event);
}
