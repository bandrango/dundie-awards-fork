package com.ninjaone.dundie_awards;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.ninjaone.dundie_awards.domain.exception.EmployeeNotFoundException;

@SpringBootTest
@TestPropertySource(properties = {
    "app.pagination.activity-default-size=10",
    "app.pagination.activity-max-size=50",
    "app.pagination.default-page-size=5"
})
class DundieAwardsAdvancedIntegrationTest {

    @Autowired
    private com.ninjaone.dundie_awards.application.service.EmployeeApplicationService employeeService;

    @Autowired
    private com.ninjaone.dundie_awards.application.service.ActivityApplicationService activityService;

    @Test
    void getAllEmployees_ShouldReturnList() {
        var employees = employeeService.getAllEmployees();
        assertThat(employees)
            .isNotNull()
            .hasSizeGreaterThan(0);
    }

    @Test
    void getEmployeeById_WithExistingId_ShouldReturnEmployee() {
        var employees = employeeService.getAllEmployees();
        assertThat(employees).isNotEmpty();
        
        Long id = employees.get(0).getId();
        var employee = employeeService.getEmployeeById(id);
        
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(id);
    }

    @Test
    void getEmployeeCount_ShouldReturnPositive() {
        long count = employeeService.getEmployeeCount();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    void getActivities_ShouldReturnPaginatedResults() {
        var activities = activityService.getActivitiesPaginated(0, 10);
        assertThat(activities)
            .isNotNull()
            .hasFieldOrPropertyWithValue("pageNumber", 0)
            .hasFieldOrPropertyWithValue("pageSize", 10);
    }

    @Test
    void getActivityCount_ShouldReturnPositive() {
        long count = activityService.getActivityCount();
        assertThat(count).isGreaterThan(0);
    }



    @Test
    void employeeNotFound_ShouldThrowException() {
        assertThatThrownBy(() -> employeeService.getEmployeeById(99999L))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("99999");
    }

    @Test
    void paginationWithDifferentSizes_ShouldRespectLimits() {
        var page1 = activityService.getActivitiesPaginated(0, 5);
        var page2 = activityService.getActivitiesPaginated(0, 50);
        var page3 = activityService.getActivitiesPaginated(0, 100); // Should be capped at max

        assertThat(page1.getPageSize()).isEqualTo(5);
        assertThat(page2.getPageSize()).isEqualTo(50);
        assertThat(page3.getPageSize()).isLessThanOrEqualTo(50); // Max size
    }
}
