package com.example.demo_ddd.shared.domain;

import java.time.LocalDateTime;

public abstract class DomainEvent {
    private final LocalDateTime occurredOn;
    private final String aggregateId;

    protected DomainEvent(String aggregateId) {
        this.aggregateId = aggregateId;
        this.occurredOn = LocalDateTime.now();
    }

    public LocalDateTime getOccurredOn() { return occurredOn; }
    public String getAggregateId() { return aggregateId; }
}