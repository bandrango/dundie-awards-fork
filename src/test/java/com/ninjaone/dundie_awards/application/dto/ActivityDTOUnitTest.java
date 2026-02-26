package com.ninjaone.dundie_awards.application.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class ActivityDTOUnitTest {

    private static final LocalDateTime NOW = LocalDateTime.now();

    @Test
    void noArgsConstructor_ShouldCreateEmptyDTO() {
        ActivityDTO dto = new ActivityDTO();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getOccurredAt()).isNull();
        assertThat(dto.getEvent()).isNull();
    }

    @Test
    void allArgsConstructor_ShouldPopulateAllFields() {
        ActivityDTO dto = new ActivityDTO(1L, NOW, "Employee created | Employee.create()");
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getOccurredAt()).isEqualTo(NOW);
        assertThat(dto.getEvent()).isEqualTo("Employee created | Employee.create()");
    }

    @Test
    void setters_ShouldUpdateFields() {
        ActivityDTO dto = new ActivityDTO();
        dto.setId(5L);
        dto.setOccurredAt(NOW);
        dto.setEvent("Employee updated | Employee.update()");

        assertThat(dto.getId()).isEqualTo(5L);
        assertThat(dto.getOccurredAt()).isEqualTo(NOW);
        assertThat(dto.getEvent()).isEqualTo("Employee updated | Employee.update()");
    }

    @Test
    void getAction_ShouldExtractActionFromEvent() {
        ActivityDTO dto = new ActivityDTO(1L, NOW, "Employee created | Employee.create()");
        assertThat(dto.getAction()).isEqualTo("Employee created");
    }

    @Test
    void getAction_WhenNoSeparator_ShouldReturnFullEvent() {
        ActivityDTO dto = new ActivityDTO(1L, NOW, "Simple event");
        assertThat(dto.getAction()).isEqualTo("Simple event");
    }

    @Test
    void equalsAndHashCode() {
        ActivityDTO dto1 = new ActivityDTO(1L, NOW, "Event 1 | Class.method()");
        ActivityDTO dto2 = new ActivityDTO(1L, NOW, "Event 1 | Class.method()");
        ActivityDTO dto3 = new ActivityDTO(2L, NOW, "Event 2 | Class.method()");

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1).isNotEqualTo(dto3);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
}
