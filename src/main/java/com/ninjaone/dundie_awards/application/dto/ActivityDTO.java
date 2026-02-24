package com.ninjaone.dundie_awards.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Activity Data Transfer Object
 * Used to transfer activity data between layers without exposing domain entities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private Long id;
    private LocalDateTime occurredAt;
    private String event;
}
