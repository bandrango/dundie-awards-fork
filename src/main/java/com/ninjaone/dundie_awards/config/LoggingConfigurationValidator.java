package com.ninjaone.dundie_awards.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ch.qos.logback.classic.LoggerContext;

import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class LoggingConfigurationValidator {

	private static final Logger logger = LoggerFactory.getLogger(LoggingConfigurationValidator.class);

	@Value("${logging.level.root:INFO}")
	private String rootLogLevel;

	@Value("${logging.level.com.ninjaone:DEBUG}")
	private String appLogLevel;

	@EventListener(ApplicationReadyEvent.class)
	public void validateLoggingConfiguration() {

		try {
			// Check logging levels
			logger.info("Root logging level configured: {}", rootLogLevel);
			logger.info("Logging level for com.ninjaone: {}", appLogLevel);

			// Check if logs directory exists
			String logPath = "logs";
			if (Files.exists(Paths.get(logPath))) {
				logger.info("Logs directory exists at: {}", logPath);
				long totalSize = Files.walk(Paths.get(logPath)).map(java.nio.file.Path::toFile)
						.mapToLong(java.io.File::length).sum();
				logger.info("Total size of log files: {} bytes", totalSize);
			} else {
				logger.warn("Logs directory DOES NOT exist at: {}", logPath);
				logger.info("Directory will be created automatically when writing logs");
			}

			// Check Logback configuration
			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			logger.info("Logging application name: {}", loggerContext.getName());
			logger.info("Number of appenders configured: {}", loggerContext.getLoggerList().size());
		} catch (Exception e) {
			logger.error("Error validating logging configuration: {}", e.getMessage(), e);
		}
	}
}
