package com.ninjaone.dundie_awards.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ninjaone.dundie_awards.domain.entity.Organization;
import com.ninjaone.dundie_awards.domain.port.OrganizationRepositoryPort;
import com.ninjaone.dundie_awards.infrastructure.persistence.OrganizationJpaEntity;
import com.ninjaone.dundie_awards.infrastructure.repository.OrganizationJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Organization Repository Adapter
 * Implements the OrganizationRepositoryPort domain port using Spring Data JPA.
 * This adapter bridges the gap between domain logic and infrastructure persistence.
 */
@Component
@RequiredArgsConstructor
public class OrganizationRepositoryAdapter implements OrganizationRepositoryPort {

    private final OrganizationJpaRepository jpaRepository;

    @Override
    public List<Organization> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomainEntity)
                .toList();
    }

    @Override
    public Optional<Organization> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomainEntity);
    }

    @Override
    public Organization save(Organization organization) {
        OrganizationJpaEntity jpaEntity = toJpaEntity(organization);
        OrganizationJpaEntity saved = jpaRepository.save(jpaEntity);
        return toDomainEntity(saved);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    /**
     * Convert JPA entity to domain entity.
     */
    private Organization toDomainEntity(OrganizationJpaEntity jpaEntity) {
        return new Organization(jpaEntity.getId(), jpaEntity.getName());
    }

    /**
     * Convert domain entity to JPA entity.
     */
    private OrganizationJpaEntity toJpaEntity(Organization organization) {
        return new OrganizationJpaEntity(organization.getId(), organization.getName());
    }
}
