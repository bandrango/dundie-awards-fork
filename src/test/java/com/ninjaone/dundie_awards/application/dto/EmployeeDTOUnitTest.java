package com.ninjaone.dundie_awards.application.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EmployeeDTOUnitTest {

    @Test
    void noArgsConstructor_ShouldCreateEmptyDTO() {
        EmployeeDTO dto = new EmployeeDTO();
        assertThat(dto)
            .hasFieldOrPropertyWithValue("id", null)
            .hasFieldOrPropertyWithValue("firstName", null)
            .hasFieldOrPropertyWithValue("lastName", null)
            .hasFieldOrPropertyWithValue("dundieAwards", null)
            .hasFieldOrPropertyWithValue("organization", null);
    }

    @Test
    void allArgsConstructor_ShouldPopulateAllFields() {
        OrganizationDTO org = new OrganizationDTO(1L, "TechCorp");
        EmployeeDTO dto = new EmployeeDTO(10L, "John", "Doe", 5, org);

        assertThat(dto)
            .hasFieldOrPropertyWithValue("id", 10L)
            .hasFieldOrPropertyWithValue("firstName", "John")
            .hasFieldOrPropertyWithValue("lastName", "Doe")
            .hasFieldOrPropertyWithValue("dundieAwards", 5)
            .hasFieldOrPropertyWithValue("organization", org);
    }

    @Test
    void setters_ShouldUpdateFields() {
        EmployeeDTO dto = new EmployeeDTO();
        OrganizationDTO org = new OrganizationDTO(1L, "TechCorp");

        dto.setId(20L);
        dto.setFirstName("Jane");
        dto.setLastName("Smith");
        dto.setDundieAwards(8);
        dto.setOrganization(org);

        assertThat(dto)
            .hasFieldOrPropertyWithValue("id", 20L)
            .hasFieldOrPropertyWithValue("firstName", "Jane")
            .hasFieldOrPropertyWithValue("lastName", "Smith")
            .hasFieldOrPropertyWithValue("dundieAwards", 8)
            .hasFieldOrPropertyWithValue("organization", org);
    }

    @Test
    void equalsAndHashCode() {
        OrganizationDTO org = new OrganizationDTO(1L, "TechCorp");
        EmployeeDTO dto1 = new EmployeeDTO(10L, "John", "Doe", 5, org);
        EmployeeDTO dto2 = new EmployeeDTO(10L, "John", "Doe", 5, org);
        EmployeeDTO dto3 = new EmployeeDTO(20L, "Jane", "Smith", 8, org);

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1).isNotEqualTo(dto3);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
}
