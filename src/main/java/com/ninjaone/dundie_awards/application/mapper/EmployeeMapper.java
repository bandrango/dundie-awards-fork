package com.ninjaone.dundie_awards.application.mapper;

import org.springframework.stereotype.Component;

import com.ninjaone.dundie_awards.application.dto.EmployeeDTO;
import com.ninjaone.dundie_awards.domain.entity.Employee;
import com.ninjaone.dundie_awards.domain.entity.Organization;

import lombok.RequiredArgsConstructor;

/**
 * Employee Mapper
 * Converts between EmployeeDTO and Employee domain entity.
 * Implements Mapper pattern to keep domain objects free of infrastructure concerns.
 */
@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final OrganizationMapper organizationMapper;

    /**
     * Convert domain entity to DTO.
     * Handles null input safely.
     */
    public EmployeeDTO toDTO(Employee employee) {
        if (employee == null) {
            return null;
        }
        return new EmployeeDTO(
            employee.getId(),
            employee.getFirstName(),
            employee.getLastName(),
            employee.getDundieAwards(),
            organizationMapper.toDTO(employee.getOrganization())
        );
    }

    /**
     * Convert DTO to domain entity.
     * Handles null input safely.
     */
    public Employee toDomain(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }
        Organization organization = organizationMapper.toDomain(dto.getOrganization());
        return new Employee(
            dto.getId(),
            dto.getFirstName(),
            dto.getLastName(),
            dto.getDundieAwards(),
            organization
        );
    }
}
