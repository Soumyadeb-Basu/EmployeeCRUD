package com.demo.employee.services;

import com.demo.employee.entities.Employee;
import com.demo.employee.exception.EmployeeNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmpService {

    ResponseEntity<Employee> saveEmployee(Employee employee);

    ResponseEntity<Employee> getEmployee(int id) throws EmployeeNotFoundException;

    ResponseEntity<List<Employee>> getEmployees() throws EmployeeNotFoundException;

    ResponseEntity<String> deleteEmpById(int id) throws EmployeeNotFoundException;

    ResponseEntity<Employee> updateEmpById(int id, Employee emp) throws EmployeeNotFoundException;

}
