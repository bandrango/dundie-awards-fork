package com.ninjaone.dundie_awards.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Activity JPA Entity
 * Database persistence model for Activity.
 * This class is specific to the persistence layer and should not be used in domain logic.
 */
@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "event", nullable = false)
    private String event;

    public ActivityJpaEntity(LocalDateTime occurredAt, String event) {
        this.occurredAt = occurredAt;
        this.event = event;
    }
}
