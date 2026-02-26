package com.ninjaone.dundie_awards.domain.exception;

/**
 * Exception thrown when an employee is not found.
 */
public class EmployeeNotFoundException extends DomainException {
    private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException(Long employeeId) {
        super("Employee with ID " + employeeId + " not found");
    }
}
