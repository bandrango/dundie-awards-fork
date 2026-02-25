package com.ninjaone.dundie_awards.config;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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
			// Validate configuration is loaded (validation runs but minimal logging)
			String logPath = "logs";
			if (!Files.exists(Paths.get(logPath))) {
				logger.warn("Logs directory does not exist at: {}", logPath);
			}
		} catch (Exception e) {
			logger.error("Error validating logging configuration: {}", e.getMessage(), e);
		}
	}
}
