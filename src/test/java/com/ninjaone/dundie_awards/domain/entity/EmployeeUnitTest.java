package com.ninjaone.dundie_awards.domain.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EmployeeUnitTest {

    private static final Organization ORG = new Organization(1L, "TechCorp");

    @Test
    void constructorWithAllFields_ShouldCreateEmployee() {
        Employee emp = new Employee(1L, "John", "Doe", 5, ORG);
        assertThat(emp.getId()).isEqualTo(1L);
        assertThat(emp.getFirstName()).isEqualTo("John");
        assertThat(emp.getLastName()).isEqualTo("Doe");
        assertThat(emp.getDundieAwards()).isEqualTo(5);
        assertThat(emp.getOrganization()).isEqualTo(ORG);
    }

    @Test
    void constructorWithoutIdAndAwards_ShouldCreateEmployee() {
        Employee emp = new Employee("John", "Doe", ORG);
        assertThat(emp.getId()).isNull();
        assertThat(emp.getFirstName()).isEqualTo("John");
        assertThat(emp.getLastName()).isEqualTo("Doe");
        assertThat(emp.getDundieAwards()).isEqualTo(0);
        assertThat(emp.getOrganization()).isEqualTo(ORG);
    }

    @Test
    void nullFirstName_ShouldThrowException() {
        assertThatThrownBy(() -> new Employee(null, "Doe", ORG))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("First name cannot be null or empty");
    }

    @Test
    void blankFirstName_ShouldThrowException() {
        assertThatThrownBy(() -> new Employee("   ", "Doe", ORG))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("First name cannot be null or empty");
    }

    @Test
    void nullLastName_ShouldThrowException() {
        assertThatThrownBy(() -> new Employee("John", null, ORG))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Last name cannot be null or empty");
    }

    @Test
    void blankLastName_ShouldThrowException() {
        assertThatThrownBy(() -> new Employee("John", "   ", ORG))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Last name cannot be null or empty");
    }

    @Test
    void nullOrganization_ShouldThrowException() {
        assertThatThrownBy(() -> new Employee("John", "Doe", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Organization cannot be null");
    }

    @Test
    void nullDundieAwards_ShouldDefaultToZero() {
        Employee emp = new Employee(1L, "John", "Doe", null, ORG);
        assertThat(emp.getDundieAwards()).isEqualTo(0);
    }

    @Test
    void getFullName_ShouldReturnFormattedName() {
        Employee emp = new Employee("John", "Doe", ORG);
        assertThat(emp.getFullName()).isEqualTo("John Doe");
    }

    @Test
    void equalsAndHashCode() {
        Employee emp1 = new Employee(1L, "John", "Doe", 5, ORG);
        Employee emp2 = new Employee(1L, "John", "Doe", 5, ORG);
        Employee emp3 = new Employee(2L, "Jane", "Smith", 3, ORG);

        assertThat(emp1).isEqualTo(emp2);
        assertThat(emp1).isNotEqualTo(emp3);
        assertThat(emp1.hashCode()).isEqualTo(emp2.hashCode());
    }
}
