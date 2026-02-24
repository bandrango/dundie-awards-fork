package com.ninjaone.dundie_awards.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Employee JPA Entity
 * Database persistence model for Employee.
 * This class is specific to the persistence layer and should not be used in domain logic.
 */
@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "dundie_awards")
    private Integer dundieAwards;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private OrganizationJpaEntity organization;

    public EmployeeJpaEntity(String firstName, String lastName, OrganizationJpaEntity organization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dundieAwards = 0;
        this.organization = organization;
    }
}
