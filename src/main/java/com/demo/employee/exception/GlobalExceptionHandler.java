package com.demo.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNullExceptions(EmployeeNotFoundException exc) {

        ExceptionResponse response= new ExceptionResponse(exc.getErrorCode(), exc.getErrorMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

}
