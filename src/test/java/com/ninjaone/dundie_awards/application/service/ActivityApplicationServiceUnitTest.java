package com.ninjaone.dundie_awards.application.service;

import com.ninjaone.dundie_awards.application.dto.ActivityDTO;
import com.ninjaone.dundie_awards.application.dto.ActivityPageDTO;
import com.ninjaone.dundie_awards.application.mapper.ActivityMapper;
import com.ninjaone.dundie_awards.domain.entity.Activity;
import com.ninjaone.dundie_awards.domain.model.PagedResult;
import com.ninjaone.dundie_awards.domain.port.ActivityRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityApplicationServiceUnitTest {

    @Mock
    private ActivityRepositoryPort activityRepository;

    @Mock
    private ActivityMapper activityMapper;

    @InjectMocks
    private ActivityApplicationService activityService;

    private LocalDateTime now;
    private Activity activity;
    private ActivityDTO activityDTO;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
        activity = new Activity(1L, now, "Employee created | Employee.create()");
        activityDTO = new ActivityDTO(1L, now, "Employee created | Employee.create()");
        
        // Configura los valores de @Value usando ReflectionTestUtils
        ReflectionTestUtils.setField(activityService, "activityDefaultSize", 20);
        ReflectionTestUtils.setField(activityService, "activityMaxSize", 100);
    }

    @Test
    void getAllActivities_ShouldReturnListOfDTOs() {
        Activity act2 = new Activity(2L, now.minusHours(1), "Employee updated | Employee.update()");
        ActivityDTO dto2 = new ActivityDTO(2L, now.minusHours(1), "Employee updated | Employee.update()");

        when(activityRepository.findAll()).thenReturn(List.of(activity, act2));
        when(activityMapper.toDTO(activity)).thenReturn(activityDTO);
        when(activityMapper.toDTO(act2)).thenReturn(dto2);

        List<ActivityDTO> result = activityService.getAllActivities();

        assertThat(result).hasSize(2);
        verify(activityRepository).findAll();
        verify(activityMapper, times(2)).toDTO(any());
    }

    @Test
    void getActivitiesPaginated_WithValidPageSize_ShouldReturnPageDTO() {
        PagedResult<Activity> pagedResult = new PagedResult<>(
                List.of(activity), 0, 20, 50, 3
        );
        when(activityRepository.findAllPagedAndSorted(0, 20)).thenReturn(pagedResult);
        when(activityMapper.toDTO(activity)).thenReturn(activityDTO);

        ActivityPageDTO result = activityService.getActivitiesPaginated(0, 20);

        assertThat(result).isNotNull();
        assertThat(result.getPageNumber()).isZero();
        assertThat(result.getPageSize()).isEqualTo(20);
        assertThat(result.getTotalElements()).isEqualTo(50);
        assertThat(result.isHasNext()).isTrue();
        assertThat(result.isHasPrevious()).isFalse();
        verify(activityRepository).findAllPagedAndSorted(0, 20);
    }

    @Test
    void getActivitiesPaginated_WithZeroPageSize_ShouldUseDefault() {
        PagedResult<Activity> pagedResult = new PagedResult<>(
                List.of(activity), 0, 20, 50, 3
        );
        when(activityRepository.findAllPagedAndSorted(0, 20)).thenReturn(pagedResult);
        when(activityMapper.toDTO(activity)).thenReturn(activityDTO);

        ActivityPageDTO result = activityService.getActivitiesPaginated(0, 0);

        assertThat(result.getPageSize()).isEqualTo(20); // Default size
        verify(activityRepository).findAllPagedAndSorted(0, 20);
    }

    @Test
    void getActivitiesPaginated_WithNegativePageSize_ShouldUseDefault() {
        PagedResult<Activity> pagedResult = new PagedResult<>(
                List.of(activity), 0, 20, 50, 3
        );
        when(activityRepository.findAllPagedAndSorted(0, 20)).thenReturn(pagedResult);
        when(activityMapper.toDTO(activity)).thenReturn(activityDTO);

        ActivityPageDTO result = activityService.getActivitiesPaginated(0, -5);

        assertThat(result.getPageSize()).isEqualTo(20); // Default size
    }

    @Test
    void getActivitiesPaginated_WithExceedMaxSize_ShouldCapAtMax() {
        PagedResult<Activity> pagedResult = new PagedResult<>(
                List.of(activity), 0, 100, 200, 2
        );
        when(activityRepository.findAllPagedAndSorted(0, 100)).thenReturn(pagedResult);
        when(activityMapper.toDTO(activity)).thenReturn(activityDTO);

        ActivityPageDTO result = activityService.getActivitiesPaginated(0, 200);

        assertThat(result.getPageSize()).isEqualTo(100); // Capped at max
        verify(activityRepository).findAllPagedAndSorted(0, 100);
    }

    @Test
    void getActivitiesPaginated_WithNegativePageNumber_ShouldDefaultToZero() {
        PagedResult<Activity> pagedResult = new PagedResult<>(
                List.of(activity), 0, 20, 50, 3
        );
        when(activityRepository.findAllPagedAndSorted(0, 20)).thenReturn(pagedResult);
        when(activityMapper.toDTO(activity)).thenReturn(activityDTO);

        ActivityPageDTO result = activityService.getActivitiesPaginated(-1, 20);

        assertThat(result.getPageNumber()).isZero();
        verify(activityRepository).findAllPagedAndSorted(0, 20);
    }

    @Test
    void getActivityCount_ShouldReturnCount() {
        when(activityRepository.count()).thenReturn(42L);

        long count = activityService.getActivityCount();

        assertThat(count).isEqualTo(42L);
        verify(activityRepository).count();
    }
}
