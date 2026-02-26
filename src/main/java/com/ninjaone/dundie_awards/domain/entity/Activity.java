package com.ninjaone.dundie_awards.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Activity Domain Entity
 * Represents an activity/event in the domain layer.
 * No framework dependencies (no JPA annotations).
 * Immutable value object with business validation.
 */
@Getter
@ToString
@EqualsAndHashCode
public class Activity {
    private final Long id;
    private final LocalDateTime occurredAt;
    private final String event;

    public Activity(Long id, LocalDateTime occurredAt, String event) {
        if (occurredAt == null) {
            throw new IllegalArgumentException("Occurred at timestamp cannot be null");
        }
        if (event == null || event.isBlank()) {
            throw new IllegalArgumentException("Event description cannot be null or empty");
        }

        this.id = id;
        this.occurredAt = occurredAt;
        this.event = event;
    }

    public Activity(LocalDateTime occurredAt, String event) {
        this(null, occurredAt, event);
    }
}
