package com.ninjaone.dundie_awards.domain.port;

import com.ninjaone.dundie_awards.domain.entity.Employee;

import java.util.List;
import java.util.Optional;

/**
 * Employee Repository Port
 * Interface that defines the contract for employee persistence.
 * This port is framework-agnostic - implementation details are in infrastructure layer.
 */
public interface EmployeeRepositoryPort {

    /**
     * Retrieve all employees.
     *
     * @return List of all employees
     */
    List<Employee> findAll();

    /**
     * Retrieve an employee by ID.
     *
     * @param id Employee ID
     * @return Optional containing the employee if found
     */
    Optional<Employee> findById(Long id);

    /**
     * Save an employee.
     *
     * @param employee The employee to save
     * @return The saved employee with ID assigned
     */
    Employee save(Employee employee);

    /**
     * Update an existing employee.
     *
     * @param employee The employee to update
     * @return The updated employee
     */
    Employee update(Employee employee);

    /**
     * Delete an employee by ID.
     *
     * @param id Employee ID
     */
    void deleteById(Long id);

    /**
     * Get the count of all employees.
     *
     * @return Number of employees
     */
    long count();
}
