package com.ninjaone.dundie_awards.application.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EmployeeDTOUnitTest {

    @Test
    void noArgsConstructor_ShouldCreateEmptyDTO() {
        EmployeeDTO dto = new EmployeeDTO();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getFirstName()).isNull();
        assertThat(dto.getLastName()).isNull();
        assertThat(dto.getDundieAwards()).isNull();
        assertThat(dto.getOrganization()).isNull();
    }

    @Test
    void allArgsConstructor_ShouldPopulateAllFields() {
        OrganizationDTO org = new OrganizationDTO(1L, "TechCorp");
        EmployeeDTO dto = new EmployeeDTO(10L, "John", "Doe", 5, org);

        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getFirstName()).isEqualTo("John");
        assertThat(dto.getLastName()).isEqualTo("Doe");
        assertThat(dto.getDundieAwards()).isEqualTo(5);
        assertThat(dto.getOrganization()).isEqualTo(org);
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

        assertThat(dto.getId()).isEqualTo(20L);
        assertThat(dto.getFirstName()).isEqualTo("Jane");
        assertThat(dto.getLastName()).isEqualTo("Smith");
        assertThat(dto.getDundieAwards()).isEqualTo(8);
        assertThat(dto.getOrganization()).isEqualTo(org);
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
