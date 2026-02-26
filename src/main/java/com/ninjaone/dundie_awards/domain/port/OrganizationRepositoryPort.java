package com.ninjaone.dundie_awards.domain.port;

import com.ninjaone.dundie_awards.domain.entity.Organization;

import java.util.List;
import java.util.Optional;

/**
 * Organization Repository Port
 * Interface that defines the contract for organization persistence.
 * This port is framework-agnostic - implementation details are in infrastructure layer.
 */
public interface OrganizationRepositoryPort {

    /**
     * Retrieve all organizations.
     *
     * @return List of all organizations
     */
    List<Organization> findAll();

    /**
     * Retrieve an organization by ID.
     *
     * @param id Organization ID
     * @return Optional containing the organization if found
     */
    Optional<Organization> findById(Long id);

    /**
     * Save an organization.
     *
     * @param organization The organization to save
     * @return The saved organization with ID assigned
     */
    Organization save(Organization organization);

    /**
     * Get the count of all organizations.
     *
     * @return Number of organizations
     */
    long count();
}
