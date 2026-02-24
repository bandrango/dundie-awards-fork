package com.ninjaone.dundie_awards.application.mapper;

import com.ninjaone.dundie_awards.application.dto.OrganizationDTO;
import com.ninjaone.dundie_awards.domain.entity.Organization;
import org.springframework.stereotype.Component;

/**
 * Organization Mapper
 * Converts between OrganizationDTO and Organization domain entity.
 */
@Component
public class OrganizationMapper {

    /**
     * Convert domain entity to DTO.
     */
    public OrganizationDTO toDTO(Organization organization) {
        if (organization == null) {
            return null;
        }
        return new OrganizationDTO(
            organization.getId(),
            organization.getName()
        );
    }

    /**
     * Convert DTO to domain entity.
     */
    public Organization toDomain(OrganizationDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Organization(
            dto.getId(),
            dto.getName()
        );
    }
}
