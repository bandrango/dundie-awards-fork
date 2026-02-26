package com.ninjaone.dundie_awards.application.mapper;

import com.ninjaone.dundie_awards.application.dto.EmployeeDTO;
import com.ninjaone.dundie_awards.application.dto.OrganizationDTO;
import com.ninjaone.dundie_awards.domain.entity.Employee;
import com.ninjaone.dundie_awards.domain.entity.Organization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperUnitTest {

    @Mock
    private OrganizationMapper organizationMapper;

    @InjectMocks
    private EmployeeMapper employeeMapper;

    private Organization organization;
    private OrganizationDTO organizationDTO;

    @BeforeEach
    void setUp() {
        organization = new Organization(1L, "TechCorp");
        organizationDTO = new OrganizationDTO(1L, "TechCorp");
    }

    @Test
    void toDTO_WithValidEmployee_ShouldConvertToDTO() {
        Employee employee = new Employee(10L, "John", "Doe", 5, organization);
        when(organizationMapper.toDTO(organization)).thenReturn(organizationDTO);

        EmployeeDTO dto = employeeMapper.toDTO(employee);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getFirstName()).isEqualTo("John");
        assertThat(dto.getLastName()).isEqualTo("Doe");
        assertThat(dto.getDundieAwards()).isEqualTo(5);
        assertThat(dto.getOrganization()).isEqualTo(organizationDTO);
        verify(organizationMapper).toDTO(organization);
    }

    @Test
    void toDTO_WithNullEmployee_ShouldReturnNull() {
        EmployeeDTO dto = employeeMapper.toDTO(null);
        assertThat(dto).isNull();
        verifyNoInteractions(organizationMapper);
    }

    @Test
    void toDomain_WithValidDTO_ShouldConvertToDomain() {
        EmployeeDTO dto = new EmployeeDTO(20L, "Jane", "Smith", 8, organizationDTO);
        when(organizationMapper.toDomain(organizationDTO)).thenReturn(organization);

        Employee employee = employeeMapper.toDomain(dto);

        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(20L);
        assertThat(employee.getFirstName()).isEqualTo("Jane");
        assertThat(employee.getLastName()).isEqualTo("Smith");
        assertThat(employee.getDundieAwards()).isEqualTo(8);
        assertThat(employee.getOrganization()).isEqualTo(organization);
        verify(organizationMapper).toDomain(organizationDTO);
    }

    @Test
    void toDomain_WithNullDTO_ShouldReturnNull() {
        Employee employee = employeeMapper.toDomain(null);
        assertThat(employee).isNull();
        verifyNoInteractions(organizationMapper);
    }
}
