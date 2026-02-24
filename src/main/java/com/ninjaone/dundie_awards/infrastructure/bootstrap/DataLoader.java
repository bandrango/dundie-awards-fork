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
 * Data Loader Initializes sample data on application startup if the database is
 * empty. Works with domain ports to maintain independence from implementation
 * details.
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

	private final EmployeeRepositoryPort employeeRepository;
	private final OrganizationRepositoryPort organizationRepository;

	@Override
	public void run(String... args) throws Exception {

		long employeeCount = employeeRepository.count();
		long organizationCount = organizationRepository.count();

		if (employeeCount == 0) {
			try {
				Organization organizationPikashu = new Organization("Pikashu");
				Organization savedPikashu = organizationRepository.save(organizationPikashu);

				employeeRepository.save(new Employee("John", "Doe", savedPikashu));
				employeeRepository.save(new Employee("Jane", "Smith", savedPikashu));
				employeeRepository.save(new Employee("Creed", "Braton", savedPikashu));

				Organization organizationSquanchy = new Organization("Squanchy");
				Organization savedSquanchy = organizationRepository.save(organizationSquanchy);

				employeeRepository.save(new Employee("Michael", "Scott", savedSquanchy));
				employeeRepository.save(new Employee("Dwight", "Schrute", savedSquanchy));
				employeeRepository.save(new Employee("Jim", "Halpert", savedSquanchy));
				employeeRepository.save(new Employee("Pam", "Beesley", savedSquanchy));
			} catch (Exception e) {
				logger.error("Error during initial data load: {}", e.getMessage(), e);
				throw e;
			}
		} else {
			logger.info("Database already contains data, skipping initial load (Employees={}, Organizations={})",
					employeeCount, organizationCount);
		}
	}
}
