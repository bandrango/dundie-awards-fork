package com.ninjaone.dundie_awards.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Organization JPA Entity
 * Database persistence model for Organization.
 * This class is specific to the persistence layer and should not be used in domain logic.
 */
@Entity
@Table(name = "organizations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public OrganizationJpaEntity(String name) {
        this.name = name;
    }
}
