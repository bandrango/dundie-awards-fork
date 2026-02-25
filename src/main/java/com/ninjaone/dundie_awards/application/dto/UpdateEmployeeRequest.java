package com.ninjaone.dundie_awards.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Update Employee Request DTO
 * Input data model for updating an existing employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest {
    
    @NotBlank(message = "First name is required and cannot be empty")
    private String firstName;
    
    @NotBlank(message = "Last name is required and cannot be empty")
    private String lastName;
}
