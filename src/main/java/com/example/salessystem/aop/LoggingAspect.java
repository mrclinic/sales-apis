package com.example.salessystem.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

	// General application logs (standard service methods)
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Dedicated logger for sales operations (goes to sales-operations.log)
    private static final Logger salesLogger = LoggerFactory.getLogger("salesLogger");

	// Log before method execution for all service methods
	@Before("execution(* com.example.salessystem.service..*(..))")
	public void logBefore(JoinPoint joinPoint) {
		logger.info("Executing method: {} with arguments: {}", joinPoint.getSignature(),
				Arrays.toString(joinPoint.getArgs()));
	}

	// Log after successful execution for all service methods
	@AfterReturning(pointcut = "execution(* com.example.salessystem.service..*(..))", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		logger.info("Method {} executed successfully, returned: {}", joinPoint.getSignature(), result);
	}

	// Log exceptions
	@AfterThrowing(pointcut = "execution(* com.example.salessystem.service..*(..))", throwing = "ex")
	public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
		logger.error("Method {} threw exception: {}", joinPoint.getSignature(), ex.getMessage(), ex);
	}

	// Log old vs new values for methods annotated with @LogUpdate
	@Before("@annotation(com.example.salessystem.aop.LogUpdate)")
	public void logOldValues(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		salesLogger.info("Updating entity. Old values (from DB or passed entity): {}", Arrays.toString(args));
	}

	@AfterReturning(pointcut = "@annotation(com.example.salessystem.aop.LogUpdate)", returning = "result")
	public void logNewValues(JoinPoint joinPoint, Object result) {
		salesLogger.info("Update completed. New values: {}", result);
	}
}
