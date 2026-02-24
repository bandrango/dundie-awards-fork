package com.ninjaone.dundie_awards.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Organization Domain Entity
 * Represents an organization in the domain layer.
 * No framework dependencies (no JPA annotations).
 * Immutable value object with business validation.
 */
@Getter
@ToString
@EqualsAndHashCode
public class Organization {
    private final Long id;
    private final String name;

    public Organization(Long id, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Organization name cannot be null or empty");
        }
        this.id = id;
        this.name = name;
    }

    public Organization(String name) {
        this(null, name);
    }
}
