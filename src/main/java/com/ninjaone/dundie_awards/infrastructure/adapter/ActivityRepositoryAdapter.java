package com.ninjaone.dundie_awards.infrastructure.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ninjaone.dundie_awards.domain.entity.Activity;
import com.ninjaone.dundie_awards.domain.port.ActivityRepositoryPort;
import com.ninjaone.dundie_awards.infrastructure.persistence.ActivityJpaEntity;
import com.ninjaone.dundie_awards.infrastructure.repository.ActivityJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Activity Repository Adapter
 * Implements the ActivityRepositoryPort domain port using Spring Data JPA.
 * This adapter bridges the gap between domain logic and infrastructure persistence.
 */
@Component
@RequiredArgsConstructor
public class ActivityRepositoryAdapter implements ActivityRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(ActivityRepositoryAdapter.class);

    private final ActivityJpaRepository jpaRepository;

    @Override
    public List<Activity> findAll() {
        logger.debug("Repository: Finding all activities");
        return jpaRepository.findAll().stream()
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Activity save(Activity activity) {
        logger.debug("Repository: Saving activity: {}", activity.getEvent());
        ActivityJpaEntity jpaEntity = toJpaEntity(activity);
        ActivityJpaEntity saved = jpaRepository.save(jpaEntity);
        return toDomainEntity(saved);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    /**
     * Convert JPA entity to domain entity.
     */
    private Activity toDomainEntity(ActivityJpaEntity jpaEntity) {
        return new Activity(jpaEntity.getId(), jpaEntity.getOccurredAt(), jpaEntity.getEvent());
    }

    /**
     * Convert domain entity to JPA entity.
     */
    private ActivityJpaEntity toJpaEntity(Activity activity) {
        ActivityJpaEntity jpaEntity = new ActivityJpaEntity();
        jpaEntity.setId(activity.getId());
        jpaEntity.setOccurredAt(activity.getOccurredAt());
        jpaEntity.setEvent(activity.getEvent());
        return jpaEntity;
    }
}
