package com.github.mszarlinski.stories.sharedkernel.event.infrastructure;

import com.github.mszarlinski.stories.sharedkernel.event.DomainEvent;
import com.github.mszarlinski.stories.sharedkernel.event.EventsPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringEventsPublisher implements EventsPublisher {

    private final ApplicationEventPublisher publisher;

    SpringEventsPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(DomainEvent event) {
        publisher.publishEvent(event);
    }
}
