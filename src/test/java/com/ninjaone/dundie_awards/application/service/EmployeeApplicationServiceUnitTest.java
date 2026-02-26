package com.ninjaone.dundie_awards.application.service;

import com.ninjaone.dundie_awards.application.dto.CreateEmployeeRequest;
import com.ninjaone.dundie_awards.application.dto.EmployeeDTO;
import com.ninjaone.dundie_awards.application.dto.OrganizationDTO;
import com.ninjaone.dundie_awards.application.dto.UpdateEmployeeRequest;
import com.ninjaone.dundie_awards.application.mapper.EmployeeMapper;
import com.ninjaone.dundie_awards.application.mapper.OrganizationMapper;
import com.ninjaone.dundie_awards.domain.entity.Employee;
import com.ninjaone.dundie_awards.domain.entity.Organization;
import com.ninjaone.dundie_awards.domain.exception.EmployeeNotFoundException;
import com.ninjaone.dundie_awards.domain.port.EmployeeRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeApplicationServiceUnitTest {

    @Mock
    private EmployeeRepositoryPort employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private OrganizationMapper organizationMapper;

    @InjectMocks
    private EmployeeApplicationService employeeService;

    private static final Long EMPLOYEE_ID = 1L;
    private static final Long ORG_ID = 1L;

    @Test
    void getAllEmployees_ShouldReturnListOfDTOs() {
        Organization org = new Organization(ORG_ID, "TechCorp");
        Employee emp1 = new Employee(1L, "John", "Doe", 5, org);
        Employee emp2 = new Employee(2L, "Jane", "Smith", 3, org);
        EmployeeDTO dto1 = new EmployeeDTO(1L, "John", "Doe", 5, new OrganizationDTO(ORG_ID, "TechCorp"));
        EmployeeDTO dto2 = new EmployeeDTO(2L, "Jane", "Smith", 3, new OrganizationDTO(ORG_ID, "TechCorp"));

        when(employeeRepository.findAll()).thenReturn(List.of(emp1, emp2));
        when(employeeMapper.toDTO(emp1)).thenReturn(dto1);
        when(employeeMapper.toDTO(emp2)).thenReturn(dto2);

        List<EmployeeDTO> result = employeeService.getAllEmployees();

        assertThat(result).hasSize(2);
        assertThat(result.get(0)).hasFieldOrPropertyWithValue("firstName", "John");
        assertThat(result.get(1)).hasFieldOrPropertyWithValue("firstName", "Jane");
        verify(employeeRepository).findAll();
    }

    @Test
    void getEmployeeById_WhenFound_ShouldReturnDTO() {
        Organization org = new Organization(ORG_ID, "TechCorp");
        Employee emp = new Employee(EMPLOYEE_ID, "John", "Doe", 5, org);
        EmployeeDTO dto = new EmployeeDTO(EMPLOYEE_ID, "John", "Doe", 5, new OrganizationDTO(ORG_ID, "TechCorp"));

        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(emp));
        when(employeeMapper.toDTO(emp)).thenReturn(dto);

        EmployeeDTO result = employeeService.getEmployeeById(EMPLOYEE_ID);

        assertThat(result).isEqualTo(dto);
        verify(employeeRepository).findById(EMPLOYEE_ID);
    }

    @Test
    void getEmployeeById_WhenNotFound_ShouldThrowException() {
        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(EMPLOYEE_ID))
                .isInstanceOf(EmployeeNotFoundException.class);
        verify(employeeRepository).findById(EMPLOYEE_ID);
    }

    @Test
    void createEmployee_ShouldSaveAndReturnDTO() {
        Organization org = new Organization(ORG_ID, "TechCorp");
        OrganizationDTO orgDTO = new OrganizationDTO(ORG_ID, "TechCorp");
        CreateEmployeeRequest request = new CreateEmployeeRequest("John", "Doe", orgDTO, 0);
        Employee saved = new Employee(EMPLOYEE_ID, "John", "Doe", 0, org);
        EmployeeDTO dto = new EmployeeDTO(EMPLOYEE_ID, "John", "Doe", 0, orgDTO);

        when(organizationMapper.toDomain(orgDTO)).thenReturn(org);
        when(employeeRepository.save(any(Employee.class))).thenReturn(saved);
        when(employeeMapper.toDTO(saved)).thenReturn(dto);

        EmployeeDTO result = employeeService.createEmployee(request);

        assertThat(result)
            .hasFieldOrPropertyWithValue("firstName", "John")
            .hasFieldOrPropertyWithValue("lastName", "Doe");
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void updateEmployee_WhenFound_ShouldUpdateAndReturnDTO() {
        Organization org = new Organization(ORG_ID, "TechCorp");
        Employee existing = new Employee(EMPLOYEE_ID, "John", "Doe", 5, org);
        Employee updated = new Employee(EMPLOYEE_ID, "Jane", "Smith", 5, org);
        UpdateEmployeeRequest request = new UpdateEmployeeRequest("Jane", "Smith");
        EmployeeDTO dto = new EmployeeDTO(EMPLOYEE_ID, "Jane", "Smith", 5, new OrganizationDTO(ORG_ID, "TechCorp"));

        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(existing));
        when(employeeRepository.update(any(Employee.class))).thenReturn(updated);
        when(employeeMapper.toDTO(updated)).thenReturn(dto);

        EmployeeDTO result = employeeService.updateEmployee(EMPLOYEE_ID, request);

        assertThat(result)
            .hasFieldOrPropertyWithValue("firstName", "Jane");
        verify(employeeRepository).update(any(Employee.class));
    }

    @Test
    void updateEmployee_WhenNotFound_ShouldThrowException() {
        UpdateEmployeeRequest request = new UpdateEmployeeRequest("Jane", "Smith");

        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.updateEmployee(EMPLOYEE_ID, request))
                .isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    void deleteEmployee_WhenFound_ShouldDelete() {
        Organization org = new Organization(ORG_ID, "TechCorp");
        Employee emp = new Employee(EMPLOYEE_ID, "John", "Doe", 5, org);

        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(emp));

        employeeService.deleteEmployee(EMPLOYEE_ID);

        verify(employeeRepository).deleteById(EMPLOYEE_ID);
    }

    @Test
    void deleteEmployee_WhenNotFound_ShouldThrowException() {
        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.deleteEmployee(EMPLOYEE_ID))
                .isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    void getEmployeeCount_ShouldReturnCount() {
        when(employeeRepository.count()).thenReturn(42L);

        long count = employeeService.getEmployeeCount();

        assertThat(count).isEqualTo(42L);
        verify(employeeRepository).count();
    }
}
