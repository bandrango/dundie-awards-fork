package com.ninjaone.dundie_awards;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ninjaone.dundie_awards.application.dto.EmployeeDTO;
import com.ninjaone.dundie_awards.application.service.EmployeeApplicationService;
import com.ninjaone.dundie_awards.application.service.ActivityApplicationService;

@SpringBootTest
class DundieAwardsApplicationTests {

	@Autowired
	private EmployeeApplicationService employeeService;

	@Autowired
	private ActivityApplicationService activityService;

	@Test
	void contextLoads() {
		assertThat(employeeService).isNotNull();
		assertThat(activityService).isNotNull();
	}

	@Test
	void testGetAllEmployees() {
		List<EmployeeDTO> employees = employeeService.getAllEmployees();
		assertThat(employees)
			.isNotNull()
			.hasSizeGreaterThan(0);
	}

	@Test
	void testGetFirstEmployee() {
		List<EmployeeDTO> employees = employeeService.getAllEmployees();
		if (employees.size() > 0) {
			EmployeeDTO employee = employees.get(0);
			assertThat(employee.getFirstName()).isNotBlank();
			assertThat(employee.getLastName()).isNotBlank();
		}
	}

	@Test
	void testGetEmployeeCount() {
		long count = employeeService.getEmployeeCount();
		assertThat(count).isGreaterThan(0);
	}

	@Test
	void testGetActivityCount() {
		long count = activityService.getActivityCount();
		assertThat(count).isNotNegative();
	}

	@Test
	void testGetActivitiesPaginatedPage0() {
		var page = activityService.getActivitiesPaginated(0, 10);
		assertThat(page).isNotNull();
		assertThat(page.getPageNumber()).isZero();
	}

	@Test
	void testActivityPagePagination() {
		var page = activityService.getActivitiesPaginated(0, 5);
		assertThat(page.getPageSize()).isEqualTo(5);
		assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(0);
	}

	@Test
	void testActivityPageHasCorrectStructure() {
		var page = activityService.getActivitiesPaginated(0, 10);
		assertThat(page.getContent()).isNotNull();
		assertThat(page.getPageNumber()).isZero();
		assertThat(page.getPageSize()).isEqualTo(10);
	}
}
