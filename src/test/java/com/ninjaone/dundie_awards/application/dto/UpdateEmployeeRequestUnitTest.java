package com.ninjaone.dundie_awards.application.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UpdateEmployeeRequestUnitTest {

    @Test
    void noArgsConstructor_ShouldCreateEmptyRequest() {
        UpdateEmployeeRequest req = new UpdateEmployeeRequest();
        assertThat(req)
            .hasFieldOrPropertyWithValue("firstName", null)
            .hasFieldOrPropertyWithValue("lastName", null);
    }

    @Test
    void allArgsConstructor_ShouldPopulateAllFields() {
        UpdateEmployeeRequest req = new UpdateEmployeeRequest("Jane", "Smith");
        assertThat(req)
            .hasFieldOrPropertyWithValue("firstName", "Jane")
            .hasFieldOrPropertyWithValue("lastName", "Smith");
    }

    @Test
    void setters_ShouldUpdateFields() {
        UpdateEmployeeRequest req = new UpdateEmployeeRequest();
        req.setFirstName("John");
        req.setLastName("Doe");

        assertThat(req)
            .hasFieldOrPropertyWithValue("firstName", "John")
            .hasFieldOrPropertyWithValue("lastName", "Doe");
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
