package com.ninjaone.dundie_awards.infrastructure.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ninjaone.dundie_awards.domain.entity.Employee;
import com.ninjaone.dundie_awards.domain.entity.Organization;
import com.ninjaone.dundie_awards.domain.port.EmployeeRepositoryPort;
import com.ninjaone.dundie_awards.infrastructure.persistence.EmployeeJpaEntity;
import com.ninjaone.dundie_awards.infrastructure.persistence.OrganizationJpaEntity;
import com.ninjaone.dundie_awards.infrastructure.repository.EmployeeJpaRepository;

import lombok.RequiredArgsConstructor;
import com.ninjaone.dundie_awards.application.annotation.Auditable;

/**
 * Employee Repository Adapter
 * Implements the EmployeeRepositoryPort domain port using Spring Data JPA.
 * This adapter bridges the gap between domain logic and infrastructure persistence.
 */
@Component
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeRepositoryAdapter.class);

    private final EmployeeJpaRepository jpaRepository;
    private final OrganizationRepositoryAdapter organizationAdapter;

    @Override
    @Auditable(action = "Fetch all employees")
    public List<Employee> findAll() {
        logger.debug("Repository: Finding all employees");
        return jpaRepository.findAll().stream()
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Auditable(action = "Fetch employee")
    public Optional<Employee> findById(Long id) {
        logger.debug("Repository: Finding employee by ID: {}", id);
        return jpaRepository.findById(id)
                .map(this::toDomainEntity);
    }

    @Override
    @Auditable(action = "Employee created")
    public Employee save(Employee employee) {
        logger.debug("Repository: Saving employee: {} {}", employee.getFirstName(), employee.getLastName());
        EmployeeJpaEntity jpaEntity = toJpaEntity(employee);
        EmployeeJpaEntity saved = jpaRepository.save(jpaEntity);
        return toDomainEntity(saved);
    }

    @Override
    @Auditable(action = "Employee updated")
    public Employee update(Employee employee) {
        logger.debug("Repository: Updating employee with ID: {}", employee.getId());
        EmployeeJpaEntity jpaEntity = toJpaEntity(employee);
        EmployeeJpaEntity updated = jpaRepository.save(jpaEntity);
        return toDomainEntity(updated);
    }

    @Override
    @Auditable(action = "Employee deleted")
    public void deleteById(Long id) {
        logger.debug("Repository: Deleting employee with ID: {}", id);
        jpaRepository.deleteById(id);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    /**
     * Convert JPA entity to domain entity.
     */
    private Employee toDomainEntity(EmployeeJpaEntity jpaEntity) {
        Organization organization = organizationAdapter.findById(jpaEntity.getOrganization().getId())
                .orElse(null);

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
