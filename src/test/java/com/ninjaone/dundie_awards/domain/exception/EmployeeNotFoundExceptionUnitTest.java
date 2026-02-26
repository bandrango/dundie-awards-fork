package com.ninjaone.dundie_awards.domain.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EmployeeNotFoundExceptionUnitTest {

    @Test
    void constructorWithEmployeeId_ShouldCreateException() {
        EmployeeNotFoundException exception = new EmployeeNotFoundException(123L);
        assertThat(exception)
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Employee with ID 123 not found");
    }

    @Test
    void exceptionShouldBeSerializable() {
        EmployeeNotFoundException exception = new EmployeeNotFoundException(456L);
        assertThat(exception.getMessage()).isEqualTo("Employee with ID 456 not found");
    }

    @Test
    void canThrowAndCatchException() {
        assertThatThrownBy(() -> {
            throw new EmployeeNotFoundException(789L);
        })
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("789");
    }
}
