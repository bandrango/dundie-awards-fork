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

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getOccurredAt()).isEqualTo(NOW);
        assertThat(dto.getEvent()).isEqualTo("Employee created | Employee.create()");
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

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNull();
        assertThat(dto.getOccurredAt()).isEqualTo(NOW);
        assertThat(dto.getEvent()).isEqualTo("Simple event");
    }

    @Test
    void toDomain_WithValidDTO_ShouldConvertToDomain() {
        ActivityDTO dto = new ActivityDTO(2L, NOW, "Employee updated | Employee.update()");
        
        Activity activity = mapper.toDomain(dto);

        assertThat(activity).isNotNull();
        assertThat(activity.getId()).isEqualTo(2L);
        assertThat(activity.getOccurredAt()).isEqualTo(NOW);
        assertThat(activity.getEvent()).isEqualTo("Employee updated | Employee.update()");
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

        assertThat(activity).isNotNull();
        assertThat(activity.getId()).isNull();
        assertThat(activity.getOccurredAt()).isEqualTo(NOW);
        assertThat(activity.getEvent()).isEqualTo("Event without ID");
    }

    @Test
    void roundTrip_DTOToDomainToDTO_ShouldPreserveData() {
        ActivityDTO original = new ActivityDTO(5L, NOW, "Test event | Test.method()");
        
        Activity domain = mapper.toDomain(original);
        ActivityDTO roundTrip = mapper.toDTO(domain);

        assertThat(roundTrip.getId()).isEqualTo(original.getId());
        assertThat(roundTrip.getOccurredAt()).isEqualTo(original.getOccurredAt());
        assertThat(roundTrip.getEvent()).isEqualTo(original.getEvent());
    }
}
