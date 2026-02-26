package com.ninjaone.dundie_awards.infrastructure.adapter;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ninjaone.dundie_awards.domain.entity.Activity;
import com.ninjaone.dundie_awards.domain.model.PagedResult;
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

    private final ActivityJpaRepository jpaRepository;

    @Override
    public List<Activity> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomainEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResult<Activity> findAllPagedAndSorted(int pageNumber, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "occurredAt");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<ActivityJpaEntity> page = jpaRepository.findAll(pageRequest);

        List<Activity> activities = page.getContent().stream()
                .map(this::toDomainEntity)
                .toList();

        return new PagedResult<>(
                activities,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Activity save(Activity activity) {
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
