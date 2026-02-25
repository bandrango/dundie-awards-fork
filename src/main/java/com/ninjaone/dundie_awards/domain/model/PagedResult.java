package com.ninjaone.dundie_awards.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Page Result
 * Generic paged result that doesn't couple domain layer with Spring Framework.
 * Immutable value object encapsulating pagination metadata.
 *
 * @param <T> The type of content
 */
@Getter
@RequiredArgsConstructor
public class PagedResult<T> {
    private final List<T> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;

    /**
     * Check if there is a next page available.
     */
    public boolean hasNext() {
        return pageNumber < totalPages - 1;
    }

    /**
     * Check if there is a previous page available.
     */
    public boolean hasPrevious() {
        return pageNumber > 0;
    }
}
