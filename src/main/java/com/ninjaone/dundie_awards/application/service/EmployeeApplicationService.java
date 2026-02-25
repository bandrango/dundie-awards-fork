package com.ninjaone.dundie_awards.application.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ninjaone.dundie_awards.application.dto.CreateEmployeeRequest;
import com.ninjaone.dundie_awards.application.dto.EmployeeDTO;
import com.ninjaone.dundie_awards.application.dto.UpdateEmployeeRequest;
import com.ninjaone.dundie_awards.application.mapper.EmployeeMapper;
import com.ninjaone.dundie_awards.application.mapper.OrganizationMapper;
import com.ninjaone.dundie_awards.domain.entity.Employee;
import com.ninjaone.dundie_awards.domain.entity.Organization;
import com.ninjaone.dundie_awards.domain.exception.EmployeeNotFoundException;
import com.ninjaone.dundie_awards.domain.port.EmployeeRepositoryPort;

import lombok.RequiredArgsConstructor;

/**
 * Employee Application Service
 * Orchestrates employee use cases and business logic.
 * Implements hexagonal architecture pattern using domain ports.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeApplicationService.class);

    private final EmployeeRepositoryPort employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final OrganizationMapper organizationMapper;

    /**
     * Use Case: Get All Employees
     * Retrieves a list of all employees in the system
     */
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    /**
     * Use Case: Get Employee By ID
     * Retrieves a specific employee by their ID
     * 
     * @param id The employee ID
     * @return EmployeeDTO if found
     * @throws EmployeeNotFoundException if employee not found
     */
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            logger.warn("Employee not found with ID: {}", id);
            throw new EmployeeNotFoundException(id);
        }
        return employeeMapper.toDTO(employee.get());
    }

    /**
     * Use Case: Create Employee
     * Creates a new employee and assigns it to an organization
     * 
     * @param request CreateEmployeeRequest with employee fields
     * @return EmployeeDTO of the created employee
     * @throws IllegalArgumentException if firstName or lastName are invalid
     */
    public EmployeeDTO createEmployee(CreateEmployeeRequest request) {
        Organization organization = organizationMapper.toDomain(request.getOrganization());
        Employee employee = new Employee(
                null,
                request.getFirstName(),
                request.getLastName(),
                request.getDundieAwards(),
                organization
        );

        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDTO(savedEmployee);
    }

    /**
     * Use Case: Update Employee
     * Updates an existing employee's firstName and lastName
     * 
     * @param id The employee ID
     * @param request UpdateEmployeeRequest with new firstName and lastName
     * @return EmployeeDTO of the updated employee
     * @throws EmployeeNotFoundException if employee not found
     */
    public EmployeeDTO updateEmployee(Long id, UpdateEmployeeRequest request) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isEmpty()) {
            logger.warn("Employee not found with ID: {}", id);
            throw new EmployeeNotFoundException(id);
        }

        Employee updatedEmployee = new Employee(
                id,
                request.getFirstName(),
                request.getLastName(),
                existingEmployee.get().getDundieAwards(),
                existingEmployee.get().getOrganization()
        );

        Employee saved = employeeRepository.update(updatedEmployee);
        return employeeMapper.toDTO(saved);
    }

    /**
     * Use Case: Delete Employee
     * Deletes an employee from the system
     * 
     * @param id The employee ID
     * @throws EmployeeNotFoundException if employee not found
     */
    public void deleteEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            logger.warn("Employee not found with ID: {}", id);
            throw new EmployeeNotFoundException(id);
        }

        employeeRepository.deleteById(id);
    }

    /**
     * Use Case: Get Employee Count
     * Returns the total number of employees in the system
     */
    public long getEmployeeCount() {
        return employeeRepository.count();
    }
}
