package com.demo.employee.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Slf4j
public class EmpServiceAspect {

    @Before(value = "execution(* com.demo.employee.services.EmpService.*(..))")
    public void beforeServiceAdvice(JoinPoint joinPoint) {
        log.info("Request to "+joinPoint.getSignature()+" started at time "+new Date());
    }

    @After(value = "execution(* com.demo.employee.services.EmpService.*(..))")
    public void afterServiceAdvice(JoinPoint joinPoint) {
        log.info("Request to "+joinPoint.getSignature()+" ended at time "+new Date());
    }
}
