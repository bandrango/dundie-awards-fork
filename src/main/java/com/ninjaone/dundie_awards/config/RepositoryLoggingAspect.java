package com.ninjaone.dundie_awards.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryLoggingAspect.class);

    @Around("execution(* com.ninjaone.dundie_awards.repository.*Repository.*(..))")
    public Object logRepositoryOperations(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();

        long startTime = System.currentTimeMillis();
        
        try {
            logger.debug("Executing: {}.{}() with {} parameter(s)", className, methodName, args.length);
            
            Object result = joinPoint.proceed();
            
            long executionTime = System.currentTimeMillis() - startTime;
            logger.debug("{}.{}() completed in {}ms", className, methodName, executionTime);
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("Error in {}.{}() after {}ms: {}", className, methodName, executionTime, e.getMessage(), e);
            throw e;
        }
    }
}
