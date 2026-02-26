package com.ninjaone.dundie_awards.infrastructure.repository;

import com.ninjaone.dundie_awards.infrastructure.persistence.EmployeeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA Repository for Employee persistence.
 * This is infrastructure-specific and should not be used directly in domain logic.
 */
public interface EmployeeJpaRepository extends JpaRepository<EmployeeJpaEntity, Long> {
    
    /**
     * Find all employees with their organizations eagerly loaded.
     * Prevents N+1 query problem by fetching organization in a single query.
     */
    @Query("SELECT DISTINCT e FROM EmployeeJpaEntity e JOIN FETCH e.organization")
    List<EmployeeJpaEntity> findAllWithOrganization();
    
    /**
     * Find employee by ID with organization eagerly loaded.
     * Prevents N+1 query problem by fetching organization in a single query.
     */
    @Query("SELECT e FROM EmployeeJpaEntity e JOIN FETCH e.organization WHERE e.id = :id")
    Optional<EmployeeJpaEntity> findByIdWithOrganization(@Param("id") Long id);
}
