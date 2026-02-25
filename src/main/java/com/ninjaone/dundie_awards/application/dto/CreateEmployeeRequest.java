package com.ninjaone.dundie_awards.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create Employee Request DTO
 * Input data model for creating a new employee.
 * Note: ID is auto-generated and Dundie Awards default to 0.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest {
    private String firstName;
    private String lastName;
    private OrganizationDTO organization;
    private Integer dundieAwards;
}
