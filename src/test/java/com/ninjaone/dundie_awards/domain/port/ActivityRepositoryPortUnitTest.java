package com.ninjaone.dundie_awards.domain.port;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ninjaone.dundie_awards.domain.entity.Activity;
import com.ninjaone.dundie_awards.domain.model.PagedResult;

class ActivityRepositoryPortUnitTest {

    @Test
    void repositoryPort_IsInterface() {
        assertThat(ActivityRepositoryPort.class.isInterface()).isTrue();
    }

    @Test
    void mockRepository_CanBeInstantiated() {
        ActivityRepositoryPort mock = mock(ActivityRepositoryPort.class);
        assertThat(mock).isNotNull();
    }

    @Test
    void findAll_CanBeMocked() {
        ActivityRepositoryPort repo = mock(ActivityRepositoryPort.class);
        Activity activity = new Activity(1L, LocalDateTime.now(), "Test event");
        
        when(repo.findAll()).thenReturn(List.of(activity));
        
        List<Activity> result = repo.findAll();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEvent()).isEqualTo("Test event");
        verify(repo).findAll();
    }

    @Test
    void findAllPagedAndSorted_CanBeMocked() {
        ActivityRepositoryPort repo = mock(ActivityRepositoryPort.class);
        Activity activity = new Activity(1L, LocalDateTime.now(), "Test event");
        PagedResult<Activity> pagedResult = new PagedResult<>(
                List.of(activity), 0, 10, 50, 5
        );
        
        when(repo.findAllPagedAndSorted(0, 10)).thenReturn(pagedResult);
        
        PagedResult<Activity> result = repo.findAllPagedAndSorted(0, 10);
        assertThat(result)
            .hasFieldOrPropertyWithValue("pageNumber", 0)
            .hasFieldOrPropertyWithValue("pageSize", 10)
            .hasFieldOrPropertyWithValue("totalElements", 50L);
    }

    @Test
    void count_CanBeMocked() {
        ActivityRepositoryPort repo = mock(ActivityRepositoryPort.class);
        when(repo.count()).thenReturn(42L);
        
        long result = repo.count();
        assertThat(result).isEqualTo(42L);
    }
}
