package com.ninjaone.dundie_awards.application.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods that should be audited.
 * Methods annotated with @Auditable will automatically generate activity logs.
 * 
 * Usage:
 * @Auditable(action = "Create employee")
 * public EmployeeDTO createEmployee(CreateEmployeeRequest request) { ... }
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    /**
     * The action description to log in the activity audit trail.
     */
    String action();
}
