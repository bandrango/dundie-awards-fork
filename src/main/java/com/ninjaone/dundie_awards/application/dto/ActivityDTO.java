package com.ninjaone.dundie_awards.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Activity Data Transfer Object
 * Used to transfer activity data between layers without exposing domain entities.
 * Provides parsed information from event string (format: "action | ClassName.methodName()")
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private Long id;
    private LocalDateTime occurredAt;
    private String event;

    /**
     * Extract the action from the event string.
     * Event format: "action | ClassName.methodName()"
     * @return The action part (e.g., "Employee created")
     */
    public String getAction() {
        if (event == null || !event.contains("|")) {
            return event;
        }
        return event.substring(0, event.indexOf("|")).trim();
    }

    /**
     * Extract the method reference from the event string.
     * Event format: "action | ClassName.methodName()"
     * @return The method reference (e.g., "EmployeeRepositoryAdapter.save()")
     */
    public String getMethodReference() {
        if (event == null || !event.contains("|")) {
            return "";
        }
        return event.substring(event.indexOf("|") + 1).trim();
    }

    /**
     * Get formatted occurred at time.
     * @return Formatted datetime (e.g., "2026-02-25 14:15:49")
     */
    public String getFormattedOccurredAt() {
        if (occurredAt == null) {
            return "";
        }
        return occurredAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Get short formatted occurred at time.
     * @return Short formatted datetime (e.g., "14:15:49")
     */
    public String getFormattedTime() {
        if (occurredAt == null) {
            return "";
        }
        return occurredAt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    /**
     * Get short formatted date.
     * @return Short formatted date (e.g., "2026-02-25")
     */
    public String getFormattedDate() {
        if (occurredAt == null) {
            return "";
        }
        return occurredAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
