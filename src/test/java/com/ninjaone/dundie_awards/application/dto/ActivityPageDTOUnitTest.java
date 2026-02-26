package com.ninjaone.dundie_awards.application.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ActivityPageDTOUnitTest {

    @Test
    void noArgsConstructor_ShouldCreateEmptyPageDTO() {
        ActivityPageDTO pageDTO = new ActivityPageDTO();
        assertThat(pageDTO.getContent()).isNull();
        assertThat(pageDTO.getPageNumber()).isEqualTo(0);
        assertThat(pageDTO.getPageSize()).isEqualTo(0);
        assertThat(pageDTO.getTotalElements()).isEqualTo(0);
        assertThat(pageDTO.getTotalPages()).isEqualTo(0);
        assertThat(pageDTO.isHasNext()).isFalse();
        assertThat(pageDTO.isHasPrevious()).isFalse();
    }

    @Test
    void allArgsConstructor_ShouldPopulateAllFields() {
        LocalDateTime now = LocalDateTime.now();
        ActivityDTO activity = new ActivityDTO(1L, now, "Employee created | Employee.create()");
        List<ActivityDTO> content = List.of(activity);

        ActivityPageDTO pageDTO = new ActivityPageDTO(content, 0, 20, 50, 3, true, false);

        assertThat(pageDTO.getContent()).isEqualTo(content);
        assertThat(pageDTO.getPageNumber()).isEqualTo(0);
        assertThat(pageDTO.getPageSize()).isEqualTo(20);
        assertThat(pageDTO.getTotalElements()).isEqualTo(50);
        assertThat(pageDTO.getTotalPages()).isEqualTo(3);
        assertThat(pageDTO.isHasNext()).isTrue();
        assertThat(pageDTO.isHasPrevious()).isFalse();
    }

    @Test
    void setters_ShouldUpdateFields() {
        ActivityPageDTO pageDTO = new ActivityPageDTO();
        LocalDateTime now = LocalDateTime.now();
        ActivityDTO activity = new ActivityDTO(2L, now, "Employee updated | Employee.update()");
        List<ActivityDTO> content = List.of(activity);

        pageDTO.setContent(content);
        pageDTO.setPageNumber(1);
        pageDTO.setPageSize(20);
        pageDTO.setTotalElements(100);
        pageDTO.setTotalPages(5);
        pageDTO.setHasNext(false);
        pageDTO.setHasPrevious(true);

        assertThat(pageDTO.getContent()).isEqualTo(content);
        assertThat(pageDTO.getPageNumber()).isEqualTo(1);
        assertThat(pageDTO.getPageSize()).isEqualTo(20);
        assertThat(pageDTO.getTotalElements()).isEqualTo(100);
        assertThat(pageDTO.getTotalPages()).isEqualTo(5);
        assertThat(pageDTO.isHasNext()).isFalse();
        assertThat(pageDTO.isHasPrevious()).isTrue();
    }

    @Test
    void equalsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        ActivityDTO activity = new ActivityDTO(1L, now, "Event | Class.method()");
        List<ActivityDTO> content = List.of(activity);

        ActivityPageDTO pageDTO1 = new ActivityPageDTO(content, 0, 20, 50, 3, true, false);
        ActivityPageDTO pageDTO2 = new ActivityPageDTO(content, 0, 20, 50, 3, true, false);
        ActivityPageDTO pageDTO3 = new ActivityPageDTO(content, 1, 20, 50, 3, false, true);

        assertThat(pageDTO1).isEqualTo(pageDTO2);
        assertThat(pageDTO1).isNotEqualTo(pageDTO3);
        assertThat(pageDTO1.hashCode()).isEqualTo(pageDTO2.hashCode());
    }
}
