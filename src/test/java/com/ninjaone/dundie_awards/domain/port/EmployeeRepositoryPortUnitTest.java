package com.ninjaone.dundie_awards.domain.port;

import com.ninjaone.dundie_awards.domain.entity.Employee;
import com.ninjaone.dundie_awards.domain.entity.Organization;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EmployeeRepositoryPortUnitTest {

    private final Organization testOrg = new Organization(1L, "TestOrg");

    @Test
    void repositoryPort_IsInterface() {
        assertThat(EmployeeRepositoryPort.class.isInterface()).isTrue();
    }

    @Test
    void mockRepository_CanBeInstantiated() {
        EmployeeRepositoryPort mock = mock(EmployeeRepositoryPort.class);
        assertThat(mock).isNotNull();
    }

    @Test
    void findById_CanBeMocked() {
        EmployeeRepositoryPort repo = mock(EmployeeRepositoryPort.class);
        Employee emp = new Employee(1L, "John", "Doe", 5, testOrg);
        
        when(repo.findById(1L)).thenReturn(Optional.of(emp));
        
        Optional<Employee> result = repo.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get()).hasFieldOrPropertyWithValue("firstName", "John");
        verify(repo).findById(1L);
    }

    @Test
    void findById_NotFound_CanBeMocked() {
        EmployeeRepositoryPort repo = mock(EmployeeRepositoryPort.class);
        
        when(repo.findById(999L)).thenReturn(Optional.empty());
        
        Optional<Employee> result = repo.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    void findAll_CanBeMocked() {
        EmployeeRepositoryPort repo = mock(EmployeeRepositoryPort.class);
        Employee emp1 = new Employee(1L, "John", "Doe", 5, testOrg);
        Employee emp2 = new Employee(2L, "Jane", "Smith", 3, testOrg);
        
        when(repo.findAll()).thenReturn(List.of(emp1, emp2));
        
        List<Employee> result = repo.findAll();
        assertThat(result).hasSize(2);
        verify(repo).findAll();
    }

    @Test
    void save_CanBeMocked() {
        EmployeeRepositoryPort repo = mock(EmployeeRepositoryPort.class);
        Employee emp = new Employee("John", "Doe", testOrg);
        Employee saved = new Employee(1L, "John", "Doe", 0, testOrg);
        
        when(repo.save(any(Employee.class))).thenReturn(saved);
        
        Employee result = repo.save(emp);
        assertThat(result.getId()).isEqualTo(1L);
        verify(repo).save(any(Employee.class));
    }

    @Test
    void update_CanBeMocked() {
        EmployeeRepositoryPort repo = mock(EmployeeRepositoryPort.class);
        Employee emp = new Employee(1L, "Jane", "Smith", 5, testOrg);
        
        when(repo.update(any(Employee.class))).thenReturn(emp);
        
        Employee result = repo.update(emp);
        assertThat(result)
            .hasFieldOrPropertyWithValue("firstName", "Jane");
    }

    @Test
    void deleteById_CanBeMocked() {
        EmployeeRepositoryPort repo = mock(EmployeeRepositoryPort.class);
        
        doNothing().when(repo).deleteById(1L);
        
        repo.deleteById(1L);
        verify(repo).deleteById(1L);
    }

    @Test
    void count_CanBeMocked() {
        EmployeeRepositoryPort repo = mock(EmployeeRepositoryPort.class);
        when(repo.count()).thenReturn(100L);
        
        long result = repo.count();
        assertThat(result).isEqualTo(100L);
    }
}
