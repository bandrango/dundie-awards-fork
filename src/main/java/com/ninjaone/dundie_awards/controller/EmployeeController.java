package com.ninjaone.dundie_awards.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ninjaone.dundie_awards.model.Employee;
import com.ninjaone.dundie_awards.repository.ActivityRepository;
import com.ninjaone.dundie_awards.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping()
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ActivityRepository activityRepository;

    // get all employees
    @GetMapping("/employees")
    @ResponseBody
    public List<Employee> getAllEmployees() {
        logger.info("GET /employees - Request to retrieve all employees");
        try {
            List<Employee> employees = employeeRepository.findAll();
            logger.debug("Successfully retrieved {} employees", employees.size());
            return employees;
        } catch (Exception e) {
            logger.error("Error retrieving employees: {}", e.getMessage(), e);
            throw e;
        }
    }

    // create employee rest api
    @PostMapping("/employees")
    @ResponseBody
    public Employee createEmployee(@RequestBody Employee employee) {
        logger.info("POST /employees - Request to create employee: {} {}", employee.getFirstName(), employee.getLastName());
        try {
            Employee savedEmployee = employeeRepository.save(employee);
            logger.info("Employee created successfully with ID: {}", savedEmployee.getId());
            return savedEmployee;
        } catch (Exception e) {
            logger.error("Error creating employee {} {}: {}", employee.getFirstName(), employee.getLastName(), e.getMessage(), e);
            throw e;
        }
    }

    // get employee by id rest api
    @GetMapping("/employees/{id}")
    @ResponseBody
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        logger.info("GET /employees/{} - Request to retrieve employee by ID", id);
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (optionalEmployee.isPresent()) {
                logger.debug("Employee found with ID: {}", id);
                return ResponseEntity.ok(optionalEmployee.get());
            } else {
                logger.warn("Employee not found with ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error retrieving employee with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // update employee rest api
    @PutMapping("/employees/{id}")
    @ResponseBody
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        logger.info("PUT /employees/{} - Request to update employee to {} {}", id, employeeDetails.getFirstName(), employeeDetails.getLastName());
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (!optionalEmployee.isPresent()) {
                logger.warn("Employee not found for update with ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Employee employee = optionalEmployee.get();
            employee.setFirstName(employeeDetails.getFirstName());
            employee.setLastName(employeeDetails.getLastName());

            Employee updatedEmployee = employeeRepository.save(employee);
            logger.info("Employee updated successfully with ID: {}", updatedEmployee.getId());
            return ResponseEntity.ok(updatedEmployee);
        } catch (Exception e) {
            logger.error("Error updating employee with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // delete employee rest api
    @DeleteMapping("/employees/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        logger.info("DELETE /employees/{} - Request to delete employee", id);
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (!optionalEmployee.isPresent()) {
                logger.warn("Employee not found for deletion with ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Employee employee = optionalEmployee.get();
            employeeRepository.delete(employee);
            logger.info("Employee deleted successfully with ID: {}", id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error deleting employee with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
