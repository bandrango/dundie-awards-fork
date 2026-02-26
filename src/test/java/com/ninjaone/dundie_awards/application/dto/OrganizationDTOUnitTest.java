package com.ninjaone.dundie_awards.application.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrganizationDTOUnitTest {

    @Test
    void noArgsConstructor_ShouldCreateEmptyDTO() {
        OrganizationDTO dto = new OrganizationDTO();
        assertThat(dto)
            .hasFieldOrPropertyWithValue("id", null)
            .hasFieldOrPropertyWithValue("name", null);
    }

    @Test
    void allArgsConstructor_ShouldPopulateAllFields() {
        OrganizationDTO dto = new OrganizationDTO(1L, "TechCorp");
        assertThat(dto)
            .hasFieldOrPropertyWithValue("id", 1L)
            .hasFieldOrPropertyWithValue("name", "TechCorp");
    }

    @Test
    void setters_ShouldUpdateFields() {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(5L);
        dto.setName("NewOrg");

        assertThat(dto)
            .hasFieldOrPropertyWithValue("id", 5L)
            .hasFieldOrPropertyWithValue("name", "NewOrg");
    }

    @Test
    void equalsAndHashCode() {
        OrganizationDTO dto1 = new OrganizationDTO(1L, "TechCorp");
        OrganizationDTO dto2 = new OrganizationDTO(1L, "TechCorp");
        OrganizationDTO dto3 = new OrganizationDTO(2L, "OtherCorp");

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1).isNotEqualTo(dto3);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
}
