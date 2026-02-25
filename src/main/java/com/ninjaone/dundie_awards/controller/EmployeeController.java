package com.ninjaone.dundie_awards.controller;

import java.util.List;

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

import lombok.RequiredArgsConstructor;

/**
 * Employee REST Controller
 * 
 * API Versions:
 * - /employees (legacy, deprecated)
 * - /api/v1/employees (current, recommended)
 */
@RestController
@RequestMapping({"/api/v1/employees"})
@RequiredArgsConstructor
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeApplicationService employeeService;

    /**
     * GET - Retrieve all employees
     */
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        logger.debug("Response: Retrieved {} employees", employees.size());
        return ResponseEntity.ok(employees);
    }

    /**
     * POST - Create new employee
     */
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody CreateEmployeeRequest request) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(request);
        logger.info("Response: Employee created successfully with ID: {}", createdEmployee.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    /**
     * GET - Retrieve employee by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        logger.debug("Response: Employee found with ID: {}", id);
        return ResponseEntity.ok(employee);
    }

    /**
     * PUT - Update employee
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody UpdateEmployeeRequest request) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, request);
        logger.info("Response: Employee updated successfully with ID: {}", id);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * DELETE - Delete employee
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        logger.info("Response: Employee deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}
