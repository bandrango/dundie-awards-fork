package com.ninjaone.dundie_awards.domain.port;

import com.ninjaone.dundie_awards.domain.entity.Activity;

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
