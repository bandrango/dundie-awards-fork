package com.ninjaone.dundie_awards.application.mapper;

import com.ninjaone.dundie_awards.application.dto.ActivityDTO;
import com.ninjaone.dundie_awards.domain.entity.Activity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class ActivityMapperUnitTest {

    private final ActivityMapper mapper = new ActivityMapper();
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Test
    void toDTO_WithValidActivity_ShouldConvertToDTO() {
        Activity activity = new Activity(1L, NOW, "Employee created | Employee.create()");
        
        ActivityDTO dto = mapper.toDTO(activity);

        assertThat(dto)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", 1L)
            .hasFieldOrPropertyWithValue("occurredAt", NOW)
            .hasFieldOrPropertyWithValue("event", "Employee created | Employee.create()");
    }

    @Test
    void toDTO_WithNullActivity_ShouldReturnNull() {
        ActivityDTO dto = mapper.toDTO(null);
        assertThat(dto).isNull();
    }

    @Test
    void toDTO_WithActivityWithoutId_ShouldConvertToDTO() {
        Activity activity = new Activity(NOW, "Simple event");
        
        ActivityDTO dto = mapper.toDTO(activity);

        assertThat(dto)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", null)
            .hasFieldOrPropertyWithValue("occurredAt", NOW)
            .hasFieldOrPropertyWithValue("event", "Simple event");
    }

    @Test
    void toDomain_WithValidDTO_ShouldConvertToDomain() {
        ActivityDTO dto = new ActivityDTO(2L, NOW, "Employee updated | Employee.update()");
        
        Activity activity = mapper.toDomain(dto);

        assertThat(activity)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", 2L)
            .hasFieldOrPropertyWithValue("occurredAt", NOW)
            .hasFieldOrPropertyWithValue("event", "Employee updated | Employee.update()");
    }

    @Test
    void toDomain_WithNullDTO_ShouldReturnNull() {
        Activity activity = mapper.toDomain(null);
        assertThat(activity).isNull();
    }

    @Test
    void toDomain_WithDTOWithoutId_ShouldConvertToDomain() {
        ActivityDTO dto = new ActivityDTO(null, NOW, "Event without ID");
        
        Activity activity = mapper.toDomain(dto);

        assertThat(activity)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", null)
            .hasFieldOrPropertyWithValue("occurredAt", NOW)
            .hasFieldOrPropertyWithValue("event", "Event without ID");
    }

    @Test
    void roundTrip_DTOToDomainToDTO_ShouldPreserveData() {
        ActivityDTO original = new ActivityDTO(5L, NOW, "Test event | Test.method()");
        
        Activity domain = mapper.toDomain(original);
        ActivityDTO roundTrip = mapper.toDTO(domain);

        assertThat(roundTrip)
            .hasFieldOrPropertyWithValue("id", original.getId())
            .hasFieldOrPropertyWithValue("occurredAt", original.getOccurredAt())
            .hasFieldOrPropertyWithValue("event", original.getEvent());
    }
}
