package com.ninjaone.dundie_awards.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ninjaone.dundie_awards.application.dto.CreateEmployeeRequest;
import com.ninjaone.dundie_awards.application.dto.EmployeeDTO;
import com.ninjaone.dundie_awards.application.dto.UpdateEmployeeRequest;
import com.ninjaone.dundie_awards.application.service.EmployeeApplicationService;
import com.ninjaone.dundie_awards.domain.exception.DomainException;

import lombok.RequiredArgsConstructor;

/**
 * Employee REST Controller
 * Handles HTTP requests for employee operations.
 * Delegates business logic to EmployeeApplicationService.
 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeApplicationService employeeService;

    /**
     * GET /employees
     * Retrieve all employees
     */
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        try {
            List<EmployeeDTO> employees = employeeService.getAllEmployees();
            logger.debug("Response: Retrieved {} employees", employees.size());
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            logger.error("Error retrieving employees: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * POST /employees
     * Create a new employee
     */
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody CreateEmployeeRequest request) {
        try {
            EmployeeDTO createdEmployee = employeeService.createEmployee(request);
            logger.info("Response: Employee created successfully with ID: {}", createdEmployee.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (DomainException e) {
            logger.warn("Domain exception: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error creating employee: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * GET /employees/{id}
     * Retrieve an employee by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        try {
            EmployeeDTO employee = employeeService.getEmployeeById(id);
            logger.debug("Response: Employee found with ID: {}", id);
            return ResponseEntity.ok(employee);
        } catch (DomainException e) {
            logger.warn("Employee not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error retrieving employee {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * PUT /employees/{id}
     * Update an existing employee
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody UpdateEmployeeRequest request) {
        try {
            EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, request);
            logger.info("Response: Employee updated successfully with ID: {}", id);
            return ResponseEntity.ok(updatedEmployee);
        } catch (DomainException e) {
            logger.warn("Employee not found for update: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating employee {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * DELETE /employees/{id}
     * Delete an employee
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            logger.info("Response: Employee deleted successfully with ID: {}", id);
            Map<String, Object> response = new HashMap<>();
            response.put("deleted", true);
            response.put("id", id);
            return ResponseEntity.ok(response);
        } catch (DomainException e) {
            logger.warn("Employee not found for deletion: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error deleting employee {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
