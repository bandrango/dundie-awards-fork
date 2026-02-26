package com.ninjaone.dundie_awards.domain.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class ActivityUnitTest {

    private static final LocalDateTime NOW = LocalDateTime.now();

    @Test
    void constructorWithAllFields_ShouldCreateActivity() {
        Activity activity = new Activity(1L, NOW, "Employee created");
        assertThat(activity.getId()).isEqualTo(1L);
        assertThat(activity.getOccurredAt()).isEqualTo(NOW);
        assertThat(activity.getEvent()).isEqualTo("Employee created");
    }

    @Test
    void constructorWithoutId_ShouldCreateActivity() {
        Activity activity = new Activity(NOW, "Employee created");
        assertThat(activity.getId()).isNull();
        assertThat(activity.getOccurredAt()).isEqualTo(NOW);
        assertThat(activity.getEvent()).isEqualTo("Employee created");
    }

    @Test
    void nullOccurredAt_ShouldThrowException() {
        assertThatThrownBy(() -> new Activity(null, "Event"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Occurred at timestamp cannot be null");
    }

    @Test
    void nullEvent_ShouldThrowException() {
        assertThatThrownBy(() -> new Activity(NOW, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Event description cannot be null or empty");
    }

    @Test
    void blankEvent_ShouldThrowException() {
        assertThatThrownBy(() -> new Activity(NOW, "   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Event description cannot be null or empty");
    }

    @Test
    void equalsAndHashCode() {
        Activity act1 = new Activity(1L, NOW, "Event");
        Activity act2 = new Activity(1L, NOW, "Event");
        Activity act3 = new Activity(2L, NOW, "Different Event");

        assertThat(act1).isEqualTo(act2);
        assertThat(act1).isNotEqualTo(act3);
        assertThat(act1.hashCode()).isEqualTo(act2.hashCode());
    }

    @Test
    void toString_ShouldContainEvent() {
        Activity activity = new Activity(1L, NOW, "Test Event");
        assertThat(activity.toString()).contains("Test Event");
    }
}
