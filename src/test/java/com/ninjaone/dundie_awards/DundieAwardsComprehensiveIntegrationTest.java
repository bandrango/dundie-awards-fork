package com.ninjaone.dundie_awards;

import com.ninjaone.dundie_awards.application.service.ActivityApplicationService;
import com.ninjaone.dundie_awards.application.service.EmployeeApplicationService;
import com.ninjaone.dundie_awards.domain.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "app.pagination.activity-default-size=15",
    "app.pagination.activity-max-size=75",
    "app.pagination.default-page-size=8"
})
class DundieAwardsComprehensiveIntegrationTest {

    @Autowired
    private EmployeeApplicationService employeeService;

    @Autowired
    private ActivityApplicationService activityService;

    @Test
    void employeeNotFound_WithInvalidId_ThrowsException() {
        assertThatThrownBy(() -> employeeService.getEmployeeById(999999L))
                .isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    void getAllActivities_ReturnsNonEmptyList() {
        var allActivities = activityService.getAllActivities();
        assertThat(allActivities).isNotEmpty();
    }

    @Test
    void paginationVariations_AllReturnValidResults() {
        // Page 0 with default size
        var page0 = activityService.getActivitiesPaginated(0, 10);
        assertThat(page0.getPageNumber()).isEqualTo(0);
        
        // Page 1
        var page1 = activityService.getActivitiesPaginated(1, 10);
        assertThat(page1.getPageNumber()).isEqualTo(1);
        
        // Large page number
        var pageHigh = activityService.getActivitiesPaginated(100, 10);
        assertThat(pageHigh).isNotNull();
    }

    @Test
    void edgeCasePagination_NegativePageNumber_DefaultsToZero() {
        var result = activityService.getActivitiesPaginated(-5, 10);
        assertThat(result.getPageNumber()).isEqualTo(0);
    }

    @Test
    void edgeCasePagination_ZeroPageSize_UsesDefault() {
        var result = activityService.getActivitiesPaginated(0, 0);
        assertThat(result.getPageSize()).isEqualTo(15); // Default from @Value
    }

    @Test
    void edgeCasePagination_NegativePageSize_UsesDefault() {
        var result = activityService.getActivitiesPaginated(0, -10);
        assertThat(result.getPageSize()).isEqualTo(15);
    }

    @Test
    void edgeCasePagination_ExceedsMaxSize_CappedAtMax() {
        var result = activityService.getActivitiesPaginated(0, 200);
        assertThat(result.getPageSize()).isLessThanOrEqualTo(75); // Max from @Value
    }

    @Test
    void employeeCountAndActivityCount_AreBothGreaterThanZero() {
        long empCount = employeeService.getEmployeeCount();
        long actCount = activityService.getActivityCount();
        
        assertThat(empCount).isGreaterThan(0);
        assertThat(actCount).isGreaterThan(0);
    }

    @Test
    void multipleEmployeeRetrievals_ReturnConsistentData() {
        var employees1 = employeeService.getAllEmployees();
        var employees2 = employeeService.getAllEmployees();
        
        assertThat(employees1.size()).isEqualTo(employees2.size());
        assertThat(employees1.get(0).getId()).isEqualTo(employees2.get(0).getId());
    }

    @Test
    void activityPaginationConsistency_DifferentPages() {
        var page1 = activityService.getActivitiesPaginated(0, 5);
        var page2 = activityService.getActivitiesPaginated(1, 5);
        
        // Both should have page size 5
        assertThat(page1.getPageSize()).isEqualTo(5);
        assertThat(page2.getPageSize()).isEqualTo(5);
        
        // Page numbers should differ
        assertThat(page1.getPageNumber()).isNotEqualTo(page2.getPageNumber());
    }

    @Test
    void retrievedEmployee_HasValidOrganization() {
        var employees = employeeService.getAllEmployees();
        assertThat(employees).isNotEmpty();
        
        var employee = employees.get(0);
        assertThat(employee.getOrganization()).isNotNull();
        assertThat(employee.getOrganization().getName()).isNotBlank();
    }

    @Test
    void retrievedActivity_HasAllRequiredFields() {
        var activities = activityService.getAllActivities();
        assertThat(activities).isNotEmpty();
        
        var activity = activities.get(0);
        assertThat(activity.getId()).isNotNull();
        assertThat(activity.getEvent()).isNotBlank();
        assertThat(activity.getOccurredAt()).isNotNull();
    }

    @Test
    void paginationMetadata_ConsistentAcrossPages() {
        var page0 = activityService.getActivitiesPaginated(0, 10);
        var page1 = activityService.getActivitiesPaginated(1, 10);
        
        // Total elements and pages should be the same
        assertThat(page0.getTotalElements()).isEqualTo(page1.getTotalElements());
        assertThat(page0.getTotalPages()).isEqualTo(page1.getTotalPages());
        
        // Page numbers should increase
        assertThat(page1.getPageNumber()).isGreaterThan(page0.getPageNumber());
    }
}
