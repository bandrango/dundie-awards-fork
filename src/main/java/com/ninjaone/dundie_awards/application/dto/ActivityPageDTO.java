package com.ninjaone.dundie_awards.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Activity Page DTO
 * Contains paginated activity data with pagination metadata
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityPageDTO {
    private List<ActivityDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
