package com.ninjaone.dundie_awards.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Organization Data Transfer Object
 * Used to transfer organization data between layers without exposing domain entities.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {
    private Long id;
    
    @NotBlank(message = "Organization name is required and cannot be empty")
    private String name;
}
