package com.ninjaone.dundie_awards.infrastructure.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ninjaone.dundie_awards.domain.entity.Employee;
import com.ninjaone.dundie_awards.domain.entity.Organization;
import com.ninjaone.dundie_awards.domain.port.EmployeeRepositoryPort;
import com.ninjaone.dundie_awards.domain.port.OrganizationRepositoryPort;

import lombok.RequiredArgsConstructor;

/**
 * Data Loader
 * Initializes sample data on application startup if the database is empty.
 * Works with domain ports to maintain independence from implementation details.
 * Implements CommandLineRunner for lazy initialization.
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

	private final EmployeeRepositoryPort employeeRepository;
	private final OrganizationRepositoryPort organizationRepository;

	@Override
	public void run(String... args) {
		if (isDatabaseEmpty()) {
			initializeData();
		}
	}

	/**
	 * Check if database is empty.
	 */
	private boolean isDatabaseEmpty() {
		return employeeRepository.count() == 0;
	}

	/**
	 * Initialize sample data.
	 */
	private void initializeData() {
		try {
			Organization pikashu = organizationRepository.save(new Organization("Pikashu"));
			employeeRepository.save(new Employee("John", "Doe", pikashu));
			employeeRepository.save(new Employee("Jane", "Smith", pikashu));
			employeeRepository.save(new Employee("Creed", "Braton", pikashu));

			Organization squanchy = organizationRepository.save(new Organization("Squanchy"));
			employeeRepository.save(new Employee("Michael", "Scott", squanchy));
			employeeRepository.save(new Employee("Dwight", "Schrute", squanchy));
			employeeRepository.save(new Employee("Jim", "Halpert", squanchy));
			employeeRepository.save(new Employee("Pam", "Beesley", squanchy));
		} catch (Exception e) {
			logger.error("Error during initial data load: {}", e.getMessage(), e);
			throw new IllegalStateException("Failed to initialize database with sample data", e);
		}
	}
}
