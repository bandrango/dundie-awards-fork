package com.ninjaone.dundie_awards.infrastructure.repository;

import com.ninjaone.dundie_awards.infrastructure.persistence.EmployeeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA Repository for Employee persistence.
 * This is infrastructure-specific and should not be used directly in domain logic.
 */
public interface EmployeeJpaRepository extends JpaRepository<EmployeeJpaEntity, Long> {
}
