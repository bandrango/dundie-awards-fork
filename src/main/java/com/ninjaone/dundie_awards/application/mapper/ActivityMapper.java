package com.ninjaone.dundie_awards.application.mapper;

import com.ninjaone.dundie_awards.application.dto.ActivityDTO;
import com.ninjaone.dundie_awards.domain.entity.Activity;
import org.springframework.stereotype.Component;

/**
 * Activity Mapper
 * Converts between ActivityDTO and Activity domain entity.
 */
@Component
public class ActivityMapper {

    /**
     * Convert domain entity to DTO.
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
