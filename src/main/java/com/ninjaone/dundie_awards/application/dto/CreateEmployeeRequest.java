package com.ninjaone.dundie_awards.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create Employee Request DTO
 * Input data model for creating a new employee.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer dundieAwards;
    private OrganizationDTO organization;
}
