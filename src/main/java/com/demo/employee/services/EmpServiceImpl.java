package com.demo.employee.services;

import com.demo.employee.entities.Employee;
import com.demo.employee.exception.EmployeeNotFoundException;
import com.demo.employee.repositories.EmpRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class EmpServiceImpl implements EmpService{

    @Autowired
    private EmpRepository repository;

    @Override
    public ResponseEntity<Employee> saveEmployee(Employee employee) {
        Employee emp= repository.save(employee);
        log.info("Employee Created successfully");
        return new ResponseEntity<>(emp, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Employee> getEmployee(int id)throws EmployeeNotFoundException {
        Employee employee;
        try
        {
            employee= repository.findById(id).get();
        }
        catch (NoSuchElementException ex) {
            log.error("Employee with given id not found..");
            throw new EmployeeNotFoundException(HttpStatus.NOT_FOUND,"Employee with id: "+id+" not found in Database..");
        }
        log.info("Employee found with id: "+id);
        return new ResponseEntity<>(employee,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployees() throws EmployeeNotFoundException {
        List<Employee> employees= repository.findAll();
        if(employees.isEmpty()) {
            log.error("No employees present in database");
            throw new EmployeeNotFoundException(HttpStatus.NOT_FOUND, "No Employees present in database...");
        }
        else {
            log.info("All employees returned");
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> deleteEmpById(int id) throws EmployeeNotFoundException {
        try {
            repository.findById(id).get();
        }
        catch (NoSuchElementException ex) {
            log.error("No element with given id found for deletion");
            throw new EmployeeNotFoundException(HttpStatus.NOT_FOUND,"Employee with id: "+id+" not found in Database for deletion..");
        }
        repository.deleteById(id);
        log.info("Employee deleted successfully");
        return new ResponseEntity<>("Employee Deleted Successfully",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> updateEmpById(int id, Employee employee) throws EmployeeNotFoundException {
        Employee emp;
        try {
            emp=repository.findById(id).get();
        } catch(NoSuchElementException ex) {
            log.error("No employee found for update");
            throw new EmployeeNotFoundException(HttpStatus.NOT_FOUND,"Employee Not found for update");
        }
        emp.setEmpName(employee.getEmpName());
        emp.setEmpEmail(employee.getEmpEmail());
        repository.save(emp);
        log.info("Employee Updated successfully");
        return new ResponseEntity<>(emp,HttpStatus.OK);
    }
}
