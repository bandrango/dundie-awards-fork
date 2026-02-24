package com.ninjaone.dundie_awards.infrastructure.repository;

import com.ninjaone.dundie_awards.infrastructure.persistence.OrganizationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA Repository for Organization persistence.
 * This is infrastructure-specific and should not be used directly in domain logic.
 */
public interface OrganizationJpaRepository extends JpaRepository<OrganizationJpaEntity, Long> {
}
