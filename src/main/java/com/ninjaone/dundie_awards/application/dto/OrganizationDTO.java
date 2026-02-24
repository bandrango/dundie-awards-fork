package com.ninjaone.dundie_awards.application.dto;

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
    private String name;
}
