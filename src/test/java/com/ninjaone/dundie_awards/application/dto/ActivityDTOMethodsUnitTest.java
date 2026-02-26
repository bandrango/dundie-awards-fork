package com.ninjaone.dundie_awards.application.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class ActivityDTOMethodsUnitTest {

    @Test
    void getAction_WithPipeDelimiter_ExtractsActionCorrectly() {
        LocalDateTime now = LocalDateTime.now();
        String eventWithAction = "Employee created | com.example.Employee.create()";
        
        ActivityDTO dto = new ActivityDTO(1L, now, eventWithAction);
        String action = dto.getAction();
        
        assertThat(action).isEqualTo("Employee created");
    }

    @Test
    void getAction_WithoutPipeDelimiter_ReturnsWholeEvent() {
        LocalDateTime now = LocalDateTime.now();
        String eventWithoutAction = "Simple event";
        
        ActivityDTO dto = new ActivityDTO(1L, now, eventWithoutAction);
        String action = dto.getAction();
        
        assertThat(action).isEqualTo("Simple event");
    }

    @Test
    void getAction_WithNullEvent_ReturnsNull() {
        LocalDateTime now = LocalDateTime.now();
        
        ActivityDTO dto = new ActivityDTO(1L, now, null);
        String action = dto.getAction();
        
        assertThat(action).isNull();
    }

    @Test
    void getAction_WithMultiplePipes_ExtractsFirstPart() {
        LocalDateTime now = LocalDateTime.now();
        String eventWithMultiplePipes = "Action | com.example.Class | extra";
        
        ActivityDTO dto = new ActivityDTO(1L, now, eventWithMultiplePipes);
        String action = dto.getAction();
        
        assertThat(action).isEqualTo("Action");
    }

    @Test
    void getMethodReference_WithValidFormat_ExtractsMethodReferenceCorrectly() {
        LocalDateTime now = LocalDateTime.now();
        String eventWithSource = "Employee created | com.ninjaone.dundie_awards.domain.entity.Employee.create()";
        
        ActivityDTO dto = new ActivityDTO(1L, now, eventWithSource);
        String methodRef = dto.getMethodReference();
        
        assertThat(methodRef).isEqualTo("com.ninjaone.dundie_awards.domain.entity.Employee.create()");
    }

    @Test
    void getMethodReference_WithoutPipeDelimiter_ReturnsEmpty() {
        LocalDateTime now = LocalDateTime.now();
        String eventWithoutSource = "Simple event";
        
        ActivityDTO dto = new ActivityDTO(1L, now, eventWithoutSource);
        String methodRef = dto.getMethodReference();
        
        assertThat(methodRef).isEmpty();
    }

    @Test
    void getMethodReference_WithNullEvent_ReturnsEmpty() {
        LocalDateTime now = LocalDateTime.now();
        
        ActivityDTO dto = new ActivityDTO(1L, now, null);
        String methodRef = dto.getMethodReference();
        
        assertThat(methodRef).isEmpty();
    }

    @Test
    void getFormattedOccurredAt_WithValidDateTime_FormatsProperly() {
        LocalDateTime testTime = LocalDateTime.of(2026, 2, 25, 14, 15, 49);
        String event = "Test event";
        
        ActivityDTO dto = new ActivityDTO(1L, testTime, event);
        String formatted = dto.getFormattedOccurredAt();
        
        assertThat(formatted).isEqualTo("2026-02-25 14:15:49");
    }

    @Test
    void getFormattedOccurredAt_WithNullDateTime_ReturnsEmpty() {
        ActivityDTO dto = new ActivityDTO(1L, null, "Test event");
        String formatted = dto.getFormattedOccurredAt();
        
        assertThat(formatted).isEmpty();
    }

    @Test
    void getFormattedTime_WithValidDateTime_FormatsTimeCorrectly() {
        LocalDateTime testTime = LocalDateTime.of(2026, 2, 25, 14, 15, 49);
        
        ActivityDTO dto = new ActivityDTO(1L, testTime, "Test event");
        String formatted = dto.getFormattedTime();
        
        assertThat(formatted).isEqualTo("14:15:49");
    }

    @Test
    void getFormattedTime_WithNullDateTime_ReturnsEmpty() {
        ActivityDTO dto = new ActivityDTO(1L, null, "Test event");
        String formatted = dto.getFormattedTime();
        
        assertThat(formatted).isEmpty();
    }

    @Test
    void getFormattedDate_WithValidDateTime_FormatsDateCorrectly() {
        LocalDateTime testTime = LocalDateTime.of(2026, 2, 25, 14, 15, 49);
        
        ActivityDTO dto = new ActivityDTO(1L, testTime, "Test event");
        String formatted = dto.getFormattedDate();
        
        assertThat(formatted).isEqualTo("2026-02-25");
    }

    @Test
    void getFormattedDate_WithNullDateTime_ReturnsEmpty() {
        ActivityDTO dto = new ActivityDTO(1L, null, "Test event");
        String formatted = dto.getFormattedDate();
        
        assertThat(formatted).isEmpty();
    }

    @Test
    void allFormattingMethods_WithCompleteEvent_ShouldFormatProperly() {
        LocalDateTime now = LocalDateTime.of(2026, 2, 25, 9, 30, 15);
        String completeEvent = "Employee created | com.ninjaone.dundie_awards.domain.entity.Employee.save()";
        
        ActivityDTO dto = new ActivityDTO(1L, now, completeEvent);
        
        assertThat(dto.getAction()).isEqualTo("Employee created");
        assertThat(dto.getMethodReference()).isEqualTo("com.ninjaone.dundie_awards.domain.entity.Employee.save()");
        assertThat(dto.getFormattedOccurredAt()).isEqualTo("2026-02-25 09:30:15");
        assertThat(dto.getFormattedTime()).isEqualTo("09:30:15");
        assertThat(dto.getFormattedDate()).isEqualTo("2026-02-25");
    }
}
