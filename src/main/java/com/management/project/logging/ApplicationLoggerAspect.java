package com.management.project.logging;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ApplicationLoggerAspect {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("within(com.management.project.controllers..*)")
	public void definePackagePointcuts() {
		//empty method just to name the location specified in the pointcut annotation.
	}
	
	@Around("definePackagePointcuts()") //This is called an "Advice" in AOP land. 
									   //Here you are giving an After adivce using the After annotation.
	public Object log(ProceedingJoinPoint jp) throws Throwable {
		logger.debug("\n \n \n");
		logger.debug("*************** Before method execution ***************");
		logger.debug("\n {}.{} () with arguments[s] {}",
				jp.getSignature().getDeclaringTypeName(),
				jp.getSignature().getName(),
				Arrays.toString(jp.getArgs()));
		logger.debug("___________________________________________________________________ \n \n \n");
		
		Object result = jp.proceed();
		
		logger.debug("*************** After method execution ***************");
		logger.debug("\n {}.{} () with arguments[s] {}",
				jp.getSignature().getDeclaringTypeName(),
				jp.getSignature().getName(),
				Arrays.toString(jp.getArgs()));
		logger.debug("___________________________________________________________________ \n \n \n");
		
		return result;
	}
}
