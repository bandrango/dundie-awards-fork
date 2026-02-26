package com.ninjaone.dundie_awards.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PagedResultUnitTest {

    @Test
    void hasNext_OnFirstPage_WithMorePages_ReturnsTrue() {
        var pagedResult = new PagedResult<>(
                List.of("item1"), 
                0,      // pageNumber = 0
                10,     // pageSize
                100,    // totalElements
                10      // totalPages = 10
        );
        
        assertThat(pagedResult.hasNext()).isTrue();
    }

    @Test
    void hasNext_OnLastPage_ReturnsFalse() {
        var pagedResult = new PagedResult<>(
                List.of("item1"),
                9,      // pageNumber = 9 (last page in 10 pages)
                10,     // pageSize
                100,    // totalElements
                10      // totalPages = 10
        );
        
        assertThat(pagedResult.hasNext()).isFalse();
    }

    @Test
    void hasNext_SinglePageOnly_ReturnsFalse() {
        var pagedResult = new PagedResult<>(
                List.of("item1"),
                0,      // pageNumber = 0
                50,     // pageSize
                25,     // totalElements
                1       // totalPages = 1
        );
        
        assertThat(pagedResult.hasNext()).isFalse();
    }

    @Test
    void hasPrevious_OnFirstPage_ReturnsFalse() {
        var pagedResult = new PagedResult<>(
                List.of("item1"),
                0,      // pageNumber = 0 (first page)
                10,     // pageSize
                100,    // totalElements
                10      // totalPages
        );
        
        assertThat(pagedResult.hasPrevious()).isFalse();
    }

    @Test
    void hasPrevious_OnSecondPage_ReturnsTrue() {
        var pagedResult = new PagedResult<>(
                List.of("item1"),
                1,      // pageNumber = 1 (second page)
                10,     // pageSize
                100,    // totalElements
                10      // totalPages
        );
        
        assertThat(pagedResult.hasPrevious()).isTrue();
    }

    @Test
    void hasPrevious_OnLastPage_ReturnsTrue() {
        var pagedResult = new PagedResult<>(
                List.of("item1"),
                9,      // pageNumber = 9 (last page)
                10,     // pageSize
                100,    // totalElements
                10      // totalPages
        );
        
        assertThat(pagedResult.hasPrevious()).isTrue();
    }

    @Test
    void getters_AllReturnCorrectValues() {
        List<String> content = List.of("item1", "item2", "item3");
        
        var pagedResult = new PagedResult<>(
                content,
                2,      // pageNumber
                15,     // pageSize
                500,    // totalElements
                34      // totalPages
        );
        
        assertThat(pagedResult)
            .hasFieldOrPropertyWithValue("content", content)
            .hasFieldOrPropertyWithValue("pageNumber", 2)
            .hasFieldOrPropertyWithValue("pageSize", 15)
            .hasFieldOrPropertyWithValue("totalElements", 500L)
            .hasFieldOrPropertyWithValue("totalPages", 34);
    }

    @Test
    void emptyContent_StillProvidesPagination() {
        var pagedResult = new PagedResult<>(
                List.of(),      // empty list
                0,              // pageNumber
                10,             // pageSize
                0,              // totalElements
                0               // totalPages
        );
        
        assertThat(pagedResult.getContent()).isEmpty();
        assertThat(pagedResult.getTotalElements()).isEqualTo(0L);
        assertThat(pagedResult.hasNext()).isFalse();
        assertThat(pagedResult.hasPrevious()).isFalse();
    }

    @Test
    void pageBoundaryConditions_MiddlePage() {
        // Testing a page in the middle of pagination
        var pagedResult = new PagedResult<>(
                List.of("item1"),
                5,      // pageNumber = 5 (middle)
                10,     // pageSize
                200,    // totalElements
                20      // totalPages
        );
        
        assertThat(pagedResult.hasNext()).isTrue();
        assertThat(pagedResult.hasPrevious()).isTrue();
    }
}
