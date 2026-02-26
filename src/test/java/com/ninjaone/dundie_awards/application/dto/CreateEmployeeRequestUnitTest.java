package com.ninjaone.dundie_awards.application.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CreateEmployeeRequestUnitTest {

    @Test
    void noArgsConstructor_ShouldCreateEmptyRequest() {
        CreateEmployeeRequest req = new CreateEmployeeRequest();
        assertThat(req.getFirstName()).isNull();
        assertThat(req.getLastName()).isNull();
        assertThat(req.getOrganization()).isNull();
        assertThat(req.getDundieAwards()).isNull();
    }

    @Test
    void allArgsConstructor_ShouldPopulateAllFields() {
        OrganizationDTO org = new OrganizationDTO(1L, "TechCorp");
        CreateEmployeeRequest req = new CreateEmployeeRequest("John", "Doe", org, 5);

        assertThat(req.getFirstName()).isEqualTo("John");
        assertThat(req.getLastName()).isEqualTo("Doe");
        assertThat(req.getOrganization()).isEqualTo(org);
        assertThat(req.getDundieAwards()).isEqualTo(5);
    }

    @Test
    void setters_ShouldUpdateFields() {
        CreateEmployeeRequest req = new CreateEmployeeRequest();
        OrganizationDTO org = new OrganizationDTO(2L, "NewOrg");

        req.setFirstName("Jane");
        req.setLastName("Smith");
        req.setOrganization(org);
        req.setDundieAwards(10);

        assertThat(req.getFirstName()).isEqualTo("Jane");
        assertThat(req.getLastName()).isEqualTo("Smith");
        assertThat(req.getOrganization()).isEqualTo(org);
        assertThat(req.getDundieAwards()).isEqualTo(10);
    }

    @Test
    void equalsAndHashCode() {
        OrganizationDTO org = new OrganizationDTO(1L, "TechCorp");
        CreateEmployeeRequest req1 = new CreateEmployeeRequest("John", "Doe", org, 5);
        CreateEmployeeRequest req2 = new CreateEmployeeRequest("John", "Doe", org, 5);
        CreateEmployeeRequest req3 = new CreateEmployeeRequest("Jane", "Smith", org, 8);

        assertThat(req1).isEqualTo(req2);
        assertThat(req1).isNotEqualTo(req3);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
    }
}
