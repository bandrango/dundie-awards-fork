package com.ninjaone.dundie_awards.application.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UpdateEmployeeRequestUnitTest {

    @Test
    void noArgsConstructor_ShouldCreateEmptyRequest() {
        UpdateEmployeeRequest req = new UpdateEmployeeRequest();
        assertThat(req.getFirstName()).isNull();
        assertThat(req.getLastName()).isNull();
    }

    @Test
    void allArgsConstructor_ShouldPopulateAllFields() {
        UpdateEmployeeRequest req = new UpdateEmployeeRequest("Jane", "Smith");
        assertThat(req.getFirstName()).isEqualTo("Jane");
        assertThat(req.getLastName()).isEqualTo("Smith");
    }

    @Test
    void setters_ShouldUpdateFields() {
        UpdateEmployeeRequest req = new UpdateEmployeeRequest();
        req.setFirstName("John");
        req.setLastName("Doe");

        assertThat(req.getFirstName()).isEqualTo("John");
        assertThat(req.getLastName()).isEqualTo("Doe");
    }

    @Test
    void equalsAndHashCode() {
        UpdateEmployeeRequest req1 = new UpdateEmployeeRequest("John", "Doe");
        UpdateEmployeeRequest req2 = new UpdateEmployeeRequest("John", "Doe");
        UpdateEmployeeRequest req3 = new UpdateEmployeeRequest("Jane", "Smith");

        assertThat(req1).isEqualTo(req2);
        assertThat(req1).isNotEqualTo(req3);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
    }
}
