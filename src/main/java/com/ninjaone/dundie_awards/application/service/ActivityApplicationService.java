package com.ninjaone.dundie_awards.application.service;

import com.ninjaone.dundie_awards.application.dto.ActivityDTO;
import com.ninjaone.dundie_awards.application.dto.ActivityPageDTO;
import com.ninjaone.dundie_awards.application.mapper.ActivityMapper;
import com.ninjaone.dundie_awards.domain.entity.Activity;
import com.ninjaone.dundie_awards.domain.model.PagedResult;
import com.ninjaone.dundie_awards.domain.port.ActivityRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Activity Application Service
 * Manages activity-related operations.
 * Pagination configuration is externalized via @Value annotations.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ActivityApplicationService {

    private final ActivityRepositoryPort activityRepository;
    private final ActivityMapper activityMapper;
    
    @Value("${app.pagination.activity-default-size:20}")
    private int activityDefaultSize;
    
    @Value("${app.pagination.activity-max-size:100}")
    private int activityMaxSize;

    /**
     * Get all activities
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> getAllActivities() {
        List<Activity> activities = activityRepository.findAll();
        return activities.stream()
                .map(activityMapper::toDTO)
                .toList();
    }

    /**
     * Get paginated and sorted activities
     * @param pageNumber Zero-based page number (defaults to 0)
     * @param pageSize Number of items per page (defaults to configured value)
     * @return ActivityPageDTO with paginated results sorted by occurred date descending
     */
    @Transactional(readOnly = true)
    public ActivityPageDTO getActivitiesPaginated(int pageNumber, int pageSize) {
        if (pageSize <= 0) {
            pageSize = activityDefaultSize;
        }
        if (pageSize > activityMaxSize) {
            pageSize = activityMaxSize;
        }
        if (pageNumber < 0) {
            pageNumber = 0;
        }

        PagedResult<Activity> pagedResult = activityRepository.findAllPagedAndSorted(pageNumber, pageSize);

        List<ActivityDTO> dtoList = pagedResult.getContent().stream()
                .map(activityMapper::toDTO)
                .toList();

        return new ActivityPageDTO(
                dtoList,
                pagedResult.getPageNumber(),
                pagedResult.getPageSize(),
                pagedResult.getTotalElements(),
                pagedResult.getTotalPages(),
                pagedResult.hasNext(),
                pagedResult.hasPrevious()
        );
    }

    /**
     * Get activity count
     */
    @Transactional(readOnly = true)
    public long getActivityCount() {
        return activityRepository.count();
    }
}
