package com.ninjaone.dundie_awards.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    
    @NotBlank(message = "First name is required and cannot be empty")
    private String firstName;
    
    @NotBlank(message = "Last name is required and cannot be empty")
    private String lastName;
    
    @NotNull(message = "Organization is required")
    @Valid
    private OrganizationDTO organization;
    
    private Integer dundieAwards;
}
