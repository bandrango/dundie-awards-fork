package com.ninjaone.dundie_awards.application.mapper;

import com.ninjaone.dundie_awards.application.dto.ActivityDTO;
import com.ninjaone.dundie_awards.domain.entity.Activity;
import org.springframework.stereotype.Component;

/**
 * Activity Mapper
 * Converts between ActivityDTO and Activity domain entity.
 * Implements Mapper pattern to keep domain objects free of infrastructure concerns.
 */
@Component
public class ActivityMapper {

    /**
     * Convert domain entity to DTO.
     * Handles null input safely.
     */
    public ActivityDTO toDTO(Activity activity) {
        if (activity == null) {
            return null;
        }
        return new ActivityDTO(
            activity.getId(),
            activity.getOccurredAt(),
            activity.getEvent()
        );
    }

    /**
     * Convert DTO to domain entity.
     * Handles null input safely.
     */
    public Activity toDomain(ActivityDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Activity(
            dto.getId(),
            dto.getOccurredAt(),
            dto.getEvent()
        );
    }
}
