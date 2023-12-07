package com.demo.employee.aspect;

import com.demo.employee.entities.Employee;
import com.demo.employee.exception.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class EmpControllerAspect {

    @Before(value = "execution(* com.demo.employee.controllers.EmpController.*(..))")
    public void beforeControllerAdvice(JoinPoint joinPoint) {
        log.info("Request to "+joinPoint.getSignature()+" started at time "+new Date());
    }

    @After(value = "execution(* com.demo.employee.controllers.EmpController.*(..))")
    public void afterControllerAdvice(JoinPoint joinPoint) {
        log.info("Request to "+joinPoint.getSignature()+" ended at time "+new Date());
    }

    @AfterReturning(value = "execution(* com.demo.employee.controllers.EmpController.getEmployeeById(..))",returning = "emp")
    public void afterReturnControllerAdvice(JoinPoint joinPoint, ResponseEntity<Employee> emp) {
        log.info("Request to "+joinPoint.getSignature()+"returned employee with id: "+ Objects.requireNonNull(emp.getBody()).getId()+" after returning advice");
    }

    @AfterThrowing(value = "execution(* com.demo.employee.controllers.EmpController.getEmployeeById(..))",throwing = "exc")
    public void afterThrowControllerAdvice(JoinPoint joinPoint, EmployeeNotFoundException exc) {
        log.info("Request to "+joinPoint.getSignature()+"resulted in exception: "+ exc.getErrorMessage()+" after throwing error in advice");
    }

}
