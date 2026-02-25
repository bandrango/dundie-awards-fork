package com.ninjaone.dundie_awards.infrastructure.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ninjaone.dundie_awards.application.annotation.Auditable;
import com.ninjaone.dundie_awards.domain.entity.Employee;
import com.ninjaone.dundie_awards.domain.entity.Organization;
import com.ninjaone.dundie_awards.domain.port.EmployeeRepositoryPort;
import com.ninjaone.dundie_awards.infrastructure.persistence.EmployeeJpaEntity;
import com.ninjaone.dundie_awards.infrastructure.persistence.OrganizationJpaEntity;
import com.ninjaone.dundie_awards.infrastructure.repository.EmployeeJpaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Employee Repository Adapter
 * Implements the EmployeeRepositoryPort domain port using Spring Data JPA.
 * This adapter bridges the gap between domain logic and infrastructure persistence.
 */
@Component
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    private final EmployeeJpaRepository jpaRepository;
    private final OrganizationRepositoryAdapter organizationAdapter;

    @Override
    @Auditable(action = "Fetch all employees")
    public List<Employee> findAll() {
        return jpaRepository.findAllWithOrganization().stream()
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Auditable(action = "Fetch employee")
    public Optional<Employee> findById(Long id) {
        return jpaRepository.findByIdWithOrganization(id)
                .map(this::toDomainEntity);
    }

    @Override
    @Auditable(action = "Employee created")
    public Employee save(Employee employee) {
        EmployeeJpaEntity jpaEntity = toJpaEntity(employee);
        EmployeeJpaEntity saved = jpaRepository.save(jpaEntity);
        return toDomainEntity(saved);
    }

    @Override
    @Auditable(action = "Employee updated")
    public Employee update(Employee employee) {
        EmployeeJpaEntity jpaEntity = toJpaEntity(employee);
        EmployeeJpaEntity updated = jpaRepository.save(jpaEntity);
        return toDomainEntity(updated);
    }

    @Override
    @Auditable(action = "Employee deleted")
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    /**
     * Convert JPA entity to domain entity.
     * Organization is already loaded via fetch join, no additional query needed.
     */
    private Employee toDomainEntity(EmployeeJpaEntity jpaEntity) {
        OrganizationJpaEntity orgJpa = jpaEntity.getOrganization();
        Organization organization = new Organization(orgJpa.getId(), orgJpa.getName());

        return new Employee(
                jpaEntity.getId(),
                jpaEntity.getFirstName(),
                jpaEntity.getLastName(),
                jpaEntity.getDundieAwards(),
                organization
        );
    }

    /**
     * Convert domain entity to JPA entity.
     */
    private EmployeeJpaEntity toJpaEntity(Employee employee) {
        EmployeeJpaEntity jpaEntity = new EmployeeJpaEntity();
        jpaEntity.setId(employee.getId());
        jpaEntity.setFirstName(employee.getFirstName());
        jpaEntity.setLastName(employee.getLastName());
        jpaEntity.setDundieAwards(employee.getDundieAwards());

        OrganizationJpaEntity orgJpa = new OrganizationJpaEntity(
                employee.getOrganization().getId(),
                employee.getOrganization().getName()
        );
        jpaEntity.setOrganization(orgJpa);

        return jpaEntity;
    }
}
