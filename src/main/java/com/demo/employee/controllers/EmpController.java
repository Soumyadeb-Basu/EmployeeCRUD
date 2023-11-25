package com.demo.employee.controllers;

import com.demo.employee.entities.Employee;
import com.demo.employee.exception.EmployeeNotFoundException;
import com.demo.employee.services.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmpController {

    @Autowired
    private EmpService service;

    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        return service.saveEmployee(employee);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) throws EmployeeNotFoundException {
        return service.getEmployee(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getEmployees() throws EmployeeNotFoundException {
        return service.getEmployees();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) throws EmployeeNotFoundException {
       return service.deleteEmpById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee emp) throws EmployeeNotFoundException {
        return service.updateEmpById(id,emp);
    }
}
