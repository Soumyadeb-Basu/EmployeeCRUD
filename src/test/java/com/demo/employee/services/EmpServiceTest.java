package com.demo.employee.services;


import com.demo.employee.entities.Employee;
import com.demo.employee.exception.EmployeeNotFoundException;
import com.demo.employee.repositories.EmpRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Test")
class EmpServiceTest {

    @Mock
    private EmpRepository repository;

    @InjectMocks
    private EmpServiceImpl service;

    private Employee employee;

    private List<Employee> employees;

    private ResponseEntity<Employee> response;

    @BeforeEach
    public void setUp() {
        employee = new Employee(1,"Tim","tim.jones@mail.com");
    }

    @Test
    @DisplayName("Testing Save Employee method")
    void testSaveEmp() {

        response= new ResponseEntity<>(employee, HttpStatus.CREATED);
        Mockito.when(repository.save(employee)).thenReturn(employee);
        Assertions.assertEquals(response,service.saveEmployee(employee));
        Mockito.verify(repository,Mockito.times(1)).save(employee);

    }

    @Test
    @DisplayName("Testing Get Employee: Success scenario")
    void testGetEmployee() throws EmployeeNotFoundException {

        response = new ResponseEntity<>(employee,HttpStatus.OK);
        Mockito.when(repository.findById(1)).thenReturn(Optional.ofNullable(employee));
        Assertions.assertEquals(response,service.getEmployee(1));

    }

    @Test
    @DisplayName("Testing Get Employee: Exception scenario")
    void testGetEmployeeException() throws EmployeeNotFoundException {

        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(EmployeeNotFoundException.class,()->service.getEmployee(1));

    }

    @Test
    @DisplayName("Testing Get all Employees: Success scenario")
    void testGetEmployees() throws EmployeeNotFoundException {

        employees=List.of(employee);
        ResponseEntity<List<Employee>> response = new ResponseEntity<>(employees,HttpStatus.OK);
        Mockito.when(repository.findAll()).thenReturn(employees);
        Assertions.assertEquals(response,service.getEmployees());

    }

    @Test
    @DisplayName("Testing Get All Employees: Exception scenario")
    void testGetEmployeesException() throws EmployeeNotFoundException {

        employees=List.of();
        Mockito.when(repository.findAll()).thenReturn(employees);
        Assertions.assertThrows(EmployeeNotFoundException.class, ()-> service.getEmployees());

    }

    @Test
    @DisplayName("Testing Delete Employee: Success scenario")
    void testDeleteEmployee() throws EmployeeNotFoundException {

        ResponseEntity<String> response= new ResponseEntity<>("Employee Deleted Successfully",HttpStatus.OK);
        Mockito.when(repository.findById(1)).thenReturn(Optional.ofNullable(employee));
        Assertions.assertEquals(response,service.deleteEmpById(1));
        Mockito.verify(repository,Mockito.times(1)).deleteById(1);

    }

    @Test
    @DisplayName("Testing Delete Employee: Exception scenario")
    void testDeleteEmployeeException() throws EmployeeNotFoundException {

        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(EmployeeNotFoundException.class,()->service.deleteEmpById(1));

    }

    @Test
    @DisplayName("Testing Update Employee: Success scenario")
    void testUpdateEmployee() throws EmployeeNotFoundException {

        Employee employee1= new Employee(1,"Jack","Jack.Winters@mail.com");
        Mockito.when(repository.findById(1)).thenReturn(Optional.ofNullable(employee));
        Assertions.assertEquals(employee1.getEmpName(), Objects.requireNonNull(service.updateEmpById(1, employee1).getBody()).getEmpName());

    }

    @Test
    @DisplayName("Testing Update Employee: Exception scenario")
    void testUpdateEmployeeException() throws EmployeeNotFoundException {

        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(EmployeeNotFoundException.class,()->service.updateEmpById(1,employee));

    }
}
