package com.ninjaone.dundie_awards.application.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for rate limiting API endpoints
 * 
 * Usage:
 * @RateLimit(maxRequests = 10, timeWindowSeconds = 60)
 * public ResponseEntity<?> getEndpoint() { ... }
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    
    /**
     * Maximum number of requests allowed in the time window
     * Default: 100 requests
     */
    int maxRequests() default 100;
    
    /**
     * Time window in seconds
     * Default: 60 seconds (1 minute)
     */
    int timeWindowSeconds() default 60;
    
    /**
     * Key to identify the rate limit bucket
     * Can use SpEL expressions like "#{#root.methodName}"
     * Default: Uses method name
     */
    String key() default "";
}
