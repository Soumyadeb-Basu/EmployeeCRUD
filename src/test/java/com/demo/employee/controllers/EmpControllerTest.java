package com.demo.employee.controllers;

import com.demo.employee.entities.Employee;
import com.demo.employee.exception.EmployeeNotFoundException;
import com.demo.employee.services.EmpServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = EmpController.class)
@DisplayName("Employee Controller Testing")
class EmpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpServiceImpl service;

    @Autowired
    private ObjectMapper mapper;

    private Employee emp;

    @BeforeEach
    public void setup() {
        emp= new Employee(1,"Raj","raj.v@mail.com");
    }

    @Test
    @DisplayName("Get a single employee successfully")
    void testGetEmployee() throws Exception {

        ResponseEntity<Employee> response= new ResponseEntity<>(emp, HttpStatus.OK);
        when(service.getEmployee(1)).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/employee/get/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.empName",is("Raj")))
                .andExpect(jsonPath("$.empEmail",is("raj.v@mail.com")));

    }

    @Test
    @DisplayName("Get a single employee Exception scenario")
    void testGetEmployeeException() throws Exception {

        when(service.getEmployee(1)).thenThrow(EmployeeNotFoundException.class);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/employee/get/1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    @DisplayName("Get list of employees successfully")
    void testGetEmployees() throws Exception {

        List<Employee> employees= Collections.singletonList(emp);
        ResponseEntity<List<Employee>> response= new ResponseEntity<>(employees, HttpStatus.OK);
        when(service.getEmployees()).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/employee/getAll"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].empName", is("Raj")))
                .andExpect(jsonPath("$.[0].empEmail", is("raj.v@mail.com")));

    }

    @Test
    @DisplayName("Get list of employees exception scenario")
    void testGetEmployeesException() throws Exception {

        ResponseEntity<List<Employee>> response= new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
        when(service.getEmployees()).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/employee/getAll"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    @DisplayName("Save Employee Successfully")
    void testSaveEmployee() throws Exception {

        ResponseEntity<Employee> response= new ResponseEntity<>(emp,HttpStatus.OK);
        when(service.saveEmployee(emp)).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/employee/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(emp)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @DisplayName("Delete Employee Successfully")
    void testDeleteEmployee() throws Exception {

        ResponseEntity<String> response= new ResponseEntity<>("Employee Deleted Successfully",HttpStatus.OK);
        when(service.deleteEmpById(1)).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/employee/delete/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @DisplayName("Delete Employee Exception Scenario")
    void testDeleteEmployeeException() throws Exception {

        ResponseEntity<String> response= new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
        when(service.deleteEmpById(1)).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/employee/delete/1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    @DisplayName("Update Employee Successfully")
    void testUpdateEmployee() throws Exception {

        ResponseEntity<Employee> response= new ResponseEntity<>(emp,HttpStatus.OK);
        Employee updateEmp= new Employee(1,"tim","tim.a@mail.com");
        when(service.updateEmpById(1,updateEmp)).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateEmp)))
                        .andDo(print())
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(jsonPath("$.empName",is("Raj")));

    }

    @Test
    @DisplayName("Update Employee Exception Scenario")
    void testUpdateEmployeeException() throws Exception {

        ResponseEntity<Employee> response= new ResponseEntity<>(new Employee(),HttpStatus.NOT_FOUND);
        Employee updateEmp= new Employee(1,"tim","tim.a@mail.com");
        when(service.updateEmpById(1,updateEmp)).thenReturn(response);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/employee/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateEmp)))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

}
