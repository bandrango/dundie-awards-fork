package com.ninjaone.dundie_awards.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for Rate Limiting
 * 
 * Binds properties from:
 * app.rate-limit.enabled
 * app.rate-limit.max-requests
 * app.rate-limit.time-window-seconds
 * 
 * Usage in application.yml:
 * app:
 *   rate-limit:
 *     enabled: true
 *     max-requests: 100
 *     time-window-seconds: 60
 */
@Component
@ConfigurationProperties(prefix = "app.rate-limit")
@Getter
@Setter
public class RateLimitConfig {
    
    /**
     * Enable/disable rate limiting
     * Default: false
     */
    private boolean enabled = false;
    
    /**
     * Global maximum number of requests allowed in the time window
     * Default: 100 requests
     */
    private int maxRequests = 100;
    
    /**
     * Global time window in seconds
     * Default: 60 seconds (1 minute)
     */
    private int timeWindowSeconds = 60;
}
