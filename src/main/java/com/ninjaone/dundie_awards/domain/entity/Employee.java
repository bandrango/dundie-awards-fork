package com.ninjaone.dundie_awards.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Employee Domain Entity
 * Represents an employee in the domain layer.
 * No framework dependencies (no JPA annotations).
 * Immutable value object with business validation.
 */
@Getter
@ToString
@EqualsAndHashCode
public class Employee {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Integer dundieAwards;
    private final Organization organization;

    public Employee(Long id, String firstName, String lastName, Integer dundieAwards, Organization organization) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (organization == null) {
            throw new IllegalArgumentException("Organization cannot be null");
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dundieAwards = dundieAwards != null ? dundieAwards : 0;
        this.organization = organization;
    }

    public Employee(String firstName, String lastName, Organization organization) {
        this(null, firstName, lastName, 0, organization);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
