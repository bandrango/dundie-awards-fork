package com.ninjaone.dundie_awards.domain.port;

import com.ninjaone.dundie_awards.domain.entity.Activity;
import com.ninjaone.dundie_awards.domain.model.PagedResult;

import java.util.List;

/**
 * Activity Repository Port
 * Interface that defines the contract for activity persistence.
 * This port is framework-agnostic - implementation details are in infrastructure layer.
 */
public interface ActivityRepositoryPort {

    /**
     * Retrieve all activities.
     *
     * @return List of all activities
     */
    List<Activity> findAll();

    /**
     * Retrieve paginated and sorted activities.
     *
     * @param pageNumber Zero-based page number
     * @param pageSize Number of elements per page
     * @return PagedResult containing activities sorted by occurred date descending
     */
    PagedResult<Activity> findAllPagedAndSorted(int pageNumber, int pageSize);

    /**
     * Save an activity.
     *
     * @param activity The activity to save
     * @return The saved activity with ID assigned
     */
    Activity save(Activity activity);

    /**
     * Get the count of all activities.
     *
     * @return Number of activities
     */
    long count();
}
