package com.github.mszarlinski.stories.publishing.domain;

import com.github.mszarlinski.stories.sharedkernel.event.DomainEvent;
import com.github.mszarlinski.stories.sharedkernel.event.EventsPublisher;

import java.util.ArrayList;
import java.util.List;

class RecordingEventsPublisher implements EventsPublisher {

    private final List<DomainEvent> events = new ArrayList<>();

    public List<DomainEvent> events() {
        return events;
    }

    @Override
    public void publish(DomainEvent event) {
        events.add(event);
    }
}
