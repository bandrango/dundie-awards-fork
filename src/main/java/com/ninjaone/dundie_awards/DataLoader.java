package com.ninjaone.dundie_awards;

import com.ninjaone.dundie_awards.model.Employee;
import com.ninjaone.dundie_awards.model.Organization;
import com.ninjaone.dundie_awards.repository.EmployeeRepository;
import com.ninjaone.dundie_awards.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;

    public DataLoader(EmployeeRepository employeeRepository, OrganizationRepository organizationRepository) {
        this.employeeRepository = employeeRepository;
        this.organizationRepository = organizationRepository;
        logger.info("DataLoader initialized - Repositories injected successfully");
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("========== Starting initial data load ==========");
        
        long employeeCount = employeeRepository.count();
        long organizationCount = organizationRepository.count();
        
        logger.info("Current records before load: Employees={}, Organizations={}", employeeCount, organizationCount);
        
        // uncomment to reseed data
        // employeeRepository.deleteAll();
        // organizationRepository.deleteAll();

        if (employeeRepository.count() == 0) {
            logger.info("Database is empty, starting sample data load");
            
            try {
                Organization organizationPikashu = new Organization("Pikashu");
                organizationRepository.save(organizationPikashu);
                logger.debug("Organization 'Pikashu' created with ID: {}", organizationPikashu.getId());

                employeeRepository.save(new Employee("John", "Doe", organizationPikashu));
                employeeRepository.save(new Employee("Jane", "Smith", organizationPikashu));
                employeeRepository.save(new Employee("Creed", "Braton", organizationPikashu));
                logger.debug("3 employees added to Organization 'Pikashu'");

                Organization organizationSquanchy = new Organization("Squanchy");
                organizationRepository.save(organizationSquanchy);
                logger.debug("Organization 'Squanchy' created with ID: {}", organizationSquanchy.getId());

                employeeRepository.save(new Employee("Michael", "Scott", organizationSquanchy));
                employeeRepository.save(new Employee("Dwight", "Schrute", organizationSquanchy));
                employeeRepository.save(new Employee("Jim", "Halpert", organizationSquanchy));
                employeeRepository.save(new Employee("Pam", "Beesley", organizationSquanchy));
                logger.debug("4 employees added to Organization 'Squanchy");
                
                long finalEmployeeCount = employeeRepository.count();
                long finalOrganizationCount = organizationRepository.count();
                logger.info("Data load completed successfully: Employees={}, Organizations={}", finalEmployeeCount, finalOrganizationCount);
            } catch (Exception e) {
                logger.error("Error during initial data load: {}", e.getMessage(), e);
                throw e;
            }
        } else {
            logger.info("Database already contains data, skipping initial load (Employees={}, Organizations={})", 
                    employeeRepository.count(), organizationRepository.count());
        }
        
        logger.info("======================================================");
    }
}
