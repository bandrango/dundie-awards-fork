package com.ninjaone.dundie_awards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DundieAwardsApplication {

	private static final Logger logger = LoggerFactory.getLogger(DundieAwardsApplication.class);

	public static void main(String[] args) {
		logger.info("========== Starting Dundie Awards Application ==========");
		SpringApplication app = new SpringApplication(DundieAwardsApplication.class);
		Environment env = app.run(args).getEnvironment();
		
		logger.info("Application started successfully");
		logger.info("Server port: {}", env.getProperty("server.port"));
		logger.info("Root logging level: {}", env.getProperty("logging.level.root"));
		logger.info("Database URL: {}", env.getProperty("spring.datasource.url"));
		logger.info("Database driver: {}", env.getProperty("spring.datasource.driver-class-name"));
		logger.info("JPA Platform: {}", env.getProperty("spring.jpa.database-platform"));
		logger.info("======================================================");
	}

}
