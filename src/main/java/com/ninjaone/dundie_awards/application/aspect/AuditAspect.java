package com.ninjaone.dundie_awards.application.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ninjaone.dundie_awards.application.annotation.Auditable;
import com.ninjaone.dundie_awards.application.service.ActivityApplicationService;
import com.ninjaone.dundie_awards.domain.entity.Activity;
import com.ninjaone.dundie_awards.domain.port.ActivityRepositoryPort;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Audit Aspect
 * 
 * Intercepts methods annotated with @Auditable and automatically logs activities
 * to the audit trail. This provides centralized, non-invasive audit logging
 * across the application without cluttering business logic.
 * 
 * Implementation of cross-cutting concern: auditing.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    private final ActivityRepositoryPort activityRepository;

    /**
     * Intercepts method execution after successful completion and logs activity.
     * 
     * @param joinPoint the join point representing the method call
     * @param auditable the Auditable annotation with action description
     */
    @AfterReturning("@annotation(auditable)")
    public void logActivity(JoinPoint joinPoint, Auditable auditable) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        try {
            String action = auditable.action();
            String eventDescription = String.format("%s | Method: %s.%s()", action, className, methodName);
            
            logger.info("AUDIT INTERCEPTED - Action: {} | Class: {} | Method: {}", action, className, methodName);

            Activity activity = new Activity(LocalDateTime.now(), eventDescription);
            
            Activity saved = activityRepository.save(activity);
            logger.info("AUDIT LOGGED - Activity ID: {} | Action: {} | Event: {}", 
                saved.getId(), action, eventDescription);

        } catch (Exception ex) {
            logger.error("AUDIT ERROR - Method: {}.{} | Error: {} | Message: {}", 
                className, methodName, ex.getClass().getSimpleName(), ex.getMessage());
        }
    }
}
