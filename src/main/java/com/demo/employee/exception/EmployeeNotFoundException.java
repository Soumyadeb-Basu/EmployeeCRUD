package com.demo.employee.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeNotFoundException extends Exception {

    private HttpStatus errorCode;

    private String errorMessage;

}
