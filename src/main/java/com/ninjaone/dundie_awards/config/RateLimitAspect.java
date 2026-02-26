package com.ninjaone.dundie_awards.config;

import com.ninjaone.dundie_awards.application.annotation.RateLimit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Aspect for globally enforcing rate limits on methods annotated with @RateLimit
 *
 * Configuration via application.properties:
 * - app.rate-limit.enabled=true/false (default: false for tests)
 *
 * Implementation: Token bucket algorithm with request counting
 */
@Aspect
@Component
@Slf4j
@ConditionalOnProperty(
    name = "app.rate-limit.enabled",
    havingValue = "true",
    matchIfMissing = false
)
public class RateLimitAspect {

    /**
     * Inner class to hold rate limit state for a bucket
     */
    private static class RateLimitBucket {
        private final int maxRequests;
        private final long timeWindowMillis;
        private AtomicInteger requestCount;
        private long windowStartTime;

        RateLimitBucket(int maxRequests, int timeWindowSeconds) {
            this.maxRequests = maxRequests;
            this.timeWindowMillis = (long) timeWindowSeconds * 1000;
            this.requestCount = new AtomicInteger(0);
            this.windowStartTime = System.currentTimeMillis();
        }

        synchronized boolean allowRequest() {
            long now = System.currentTimeMillis();
            long elapsedTime = now - windowStartTime;

            if (elapsedTime >= timeWindowMillis) {
                // Reset the window
                windowStartTime = now;
                requestCount.set(1);
                return true;
            }

            int currentCount = requestCount.incrementAndGet();
            return currentCount <= maxRequests;
        }
    }

    private final ConcurrentHashMap<String, RateLimitBucket> buckets = new ConcurrentHashMap<>();

    /**
     * Intercepts methods annotated with @RateLimit and enforces rate limiting
     *
     * @throws ResponseStatusException (429 Too Many Requests) if limit exceeded
     */
    @Before("@annotation(rateLimit)")
    public void enforceRateLimit(JoinPoint joinPoint, RateLimit rateLimit) {
        String bucketKey = resolveBucketKey(joinPoint, rateLimit);
        RateLimitBucket bucket = buckets.computeIfAbsent(
            bucketKey,
            k -> new RateLimitBucket(rateLimit.maxRequests(), rateLimit.timeWindowSeconds())
        );

        if (!bucket.allowRequest()) {
            log.warn("Rate limit exceeded for: {} (max: {}/{}s)",
                bucketKey, rateLimit.maxRequests(), rateLimit.timeWindowSeconds());
            throw new ResponseStatusException(
                HttpStatus.TOO_MANY_REQUESTS,
                String.format("Rate limit exceeded: max %d requests per %d seconds",
                    rateLimit.maxRequests(), rateLimit.timeWindowSeconds())
            );
        }

        log.debug("Rate limit check passed for: {}", bucketKey);
    }

    /**
     * Resolve the bucket key from @RateLimit annotation
     * Default: uses method name
     */
    private String resolveBucketKey(JoinPoint joinPoint, RateLimit rateLimit) {
        if (rateLimit.key() != null && !rateLimit.key().isEmpty()) {
            return rateLimit.key();
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method.getDeclaringClass().getSimpleName() + "." + method.getName();
    }
}
