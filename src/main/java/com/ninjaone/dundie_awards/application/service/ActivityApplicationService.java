package com.ninjaone.dundie_awards.application.service;

import com.ninjaone.dundie_awards.application.dto.ActivityDTO;
import com.ninjaone.dundie_awards.application.mapper.ActivityMapper;
import com.ninjaone.dundie_awards.domain.entity.Activity;
import com.ninjaone.dundie_awards.domain.port.ActivityRepositoryPort;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Activity Application Service
 * Manages activity-related operations.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ActivityApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityApplicationService.class);

    private final ActivityRepositoryPort activityRepository;
    private final ActivityMapper activityMapper;

    /**
     * Use Case: Get all activities
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> getAllActivities() {
        logger.info("Use Case: Retrieving all activities");
        List<Activity> activities = activityRepository.findAll();
        logger.debug("Retrieved {} activities", activities.size());
        return activities.stream()
                .map(activityMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get activity count
     */
    @Transactional(readOnly = true)
    public long getActivityCount() {
        return activityRepository.count();
    }
}
