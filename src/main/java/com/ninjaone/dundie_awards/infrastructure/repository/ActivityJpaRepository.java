package com.ninjaone.dundie_awards.infrastructure.repository;

import com.ninjaone.dundie_awards.infrastructure.persistence.ActivityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA Repository for Activity persistence.
 * This is infrastructure-specific and should not be used directly in domain logic.
 */
public interface ActivityJpaRepository extends JpaRepository<ActivityJpaEntity, Long> {
}
