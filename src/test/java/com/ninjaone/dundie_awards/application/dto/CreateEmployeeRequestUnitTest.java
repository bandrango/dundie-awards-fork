package com.ninjaone.dundie_awards.application.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CreateEmployeeRequestUnitTest {

    @Test
    void noArgsConstructor_ShouldCreateEmptyRequest() {
        CreateEmployeeRequest req = new CreateEmployeeRequest();
        assertThat(req)
            .hasFieldOrPropertyWithValue("firstName", null)
            .hasFieldOrPropertyWithValue("lastName", null)
            .hasFieldOrPropertyWithValue("organization", null)
            .hasFieldOrPropertyWithValue("dundieAwards", null);
    }

    @Test
    void allArgsConstructor_ShouldPopulateAllFields() {
        OrganizationDTO org = new OrganizationDTO(1L, "TechCorp");
        CreateEmployeeRequest req = new CreateEmployeeRequest("John", "Doe", org, 5);

        assertThat(req)
            .hasFieldOrPropertyWithValue("firstName", "John")
            .hasFieldOrPropertyWithValue("lastName", "Doe")
            .hasFieldOrPropertyWithValue("organization", org)
            .hasFieldOrPropertyWithValue("dundieAwards", 5);
    }

    @Test
    void setters_ShouldUpdateFields() {
        CreateEmployeeRequest req = new CreateEmployeeRequest();
        OrganizationDTO org = new OrganizationDTO(2L, "NewOrg");

        req.setFirstName("Jane");
        req.setLastName("Smith");
        req.setOrganization(org);
        req.setDundieAwards(10);

        assertThat(req)
            .hasFieldOrPropertyWithValue("firstName", "Jane")
            .hasFieldOrPropertyWithValue("lastName", "Smith")
            .hasFieldOrPropertyWithValue("organization", org)
            .hasFieldOrPropertyWithValue("dundieAwards", 10);
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
