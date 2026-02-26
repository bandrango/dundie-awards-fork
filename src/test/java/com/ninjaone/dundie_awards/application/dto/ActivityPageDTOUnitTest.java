package com.ninjaone.dundie_awards.application.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ActivityPageDTOUnitTest {

    @Test
    void noArgsConstructor_ShouldCreateEmptyPageDTO() {
        ActivityPageDTO pageDTO = new ActivityPageDTO();
        assertThat(pageDTO)
            .hasFieldOrPropertyWithValue("content", null)
            .hasFieldOrPropertyWithValue("pageNumber", 0)
            .hasFieldOrPropertyWithValue("pageSize", 0)
            .hasFieldOrPropertyWithValue("totalElements", 0L)
            .hasFieldOrPropertyWithValue("totalPages", 0)
            .hasFieldOrPropertyWithValue("hasNext", false)
            .hasFieldOrPropertyWithValue("hasPrevious", false);
    }

    @Test
    void allArgsConstructor_ShouldPopulateAllFields() {
        LocalDateTime now = LocalDateTime.now();
        ActivityDTO activity = new ActivityDTO(1L, now, "Employee created | Employee.create()");
        List<ActivityDTO> content = List.of(activity);

        ActivityPageDTO pageDTO = new ActivityPageDTO(content, 0, 20, 50, 3, true, false);

        assertThat(pageDTO)
            .hasFieldOrPropertyWithValue("content", content)
            .hasFieldOrPropertyWithValue("pageNumber", 0)
            .hasFieldOrPropertyWithValue("pageSize", 20)
            .hasFieldOrPropertyWithValue("totalElements", 50L)
            .hasFieldOrPropertyWithValue("totalPages", 3)
            .hasFieldOrPropertyWithValue("hasNext", true)
            .hasFieldOrPropertyWithValue("hasPrevious", false);
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

        assertThat(pageDTO)
            .hasFieldOrPropertyWithValue("content", content)
            .hasFieldOrPropertyWithValue("pageNumber", 1)
            .hasFieldOrPropertyWithValue("pageSize", 20)
            .hasFieldOrPropertyWithValue("totalElements", 100L)
            .hasFieldOrPropertyWithValue("totalPages", 5)
            .hasFieldOrPropertyWithValue("hasNext", false)
            .hasFieldOrPropertyWithValue("hasPrevious", true);
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
