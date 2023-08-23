package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.controller.EmployeeController;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeApiTests {

    private MockMvc mockMvcClient;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public EmployeeApiTests(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    @BeforeEach
    public void setUp() {
        employeeRepository.cleanAllEmployees();
        mockMvcClient = MockMvcBuilders.standaloneSetup(new EmployeeController(employeeRepository, companyRepository)).build();
    }

    @Test
    void should_return_all_employees_when_perform_get_employees() throws Exception {
        // Given
        Employee alice = employeeRepository.saveEmployee(new Employee(1L, "Alice", 24,
                "Female", 9000, 1L));

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(alice.getId()))
                .andExpect(jsonPath("$[0].name").value(alice.getName()))
                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
                .andExpect(jsonPath("$[0].salary").value(alice.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(alice.getCompanyId()));
    }

    @Test
    void should_return_the_employee_when_perform_get_employee_given_the_employee_id() throws Exception {
        //given
        Employee alice = employeeRepository.saveEmployee(new Employee(1L, "Alice", 24, "Female", 9000, 1L));
        employeeRepository.saveEmployee(new Employee(2L, "Bob", 24, "Male", 9000, 1L));

        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + alice.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(alice.getId()))
                .andExpect(jsonPath("$.name").value(alice.getName()))
                .andExpect(jsonPath("$.age").value(alice.getAge()))
                .andExpect(jsonPath("$.gender").value(alice.getGender()))
                .andExpect(jsonPath("$.salary").value(alice.getSalary()))
                .andExpect(jsonPath("$.companyId").value(alice.getCompanyId()));
    }

    @Test
    void should_return_list_of_employee_by_given_gender_when_perform_get_employee_given_gender() throws Exception {
        //Given
        String employeeGender = "female";
        List<Employee> femaleEmployees = employeeRepository.findByGender(employeeGender);

        // When Then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees").param("gender", employeeGender))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(femaleEmployees.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender").value(Matchers.everyItem(Matchers.is(employeeGender))));
    }

    @Test
    void should_return_updated_employee_when_perform_put_employee_given_existing_employee_id_and_updated_data() throws Exception {
        // Given
        Employee existingEmployee = employeeRepository.saveEmployee(new Employee(1L, "Alice", 24, "Female", 9000, 1L));
        Employee updatedEmployee = new Employee(existingEmployee.getId(), "Juliet", 25, "Female", 9500, 1L);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/" + existingEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingEmployee.getId()))
                .andExpect(jsonPath("$.name").value(updatedEmployee.getName()))
                .andExpect(jsonPath("$.age").value(updatedEmployee.getAge()))
                .andExpect(jsonPath("$.gender").value(updatedEmployee.getGender()))
                .andExpect(jsonPath("$.salary").value(updatedEmployee.getSalary()))
                .andExpect(jsonPath("$.companyId").value(updatedEmployee.getCompanyId()));
    }


    @Test
    void should_return_paginated_employees_when_perform_get_employees_with_pagination() throws Exception {
        // Given
        employeeRepository.saveEmployee(new Employee(1L, "Alice", 24, "Female", 9000, 1L));
        employeeRepository.saveEmployee(new Employee(2L, "Bob", 25, "Male", 8500, 1L));
        employeeRepository.saveEmployee(new Employee(3L, "Charlie", 28, "Male", 9500, 1L));

        // When,
        int pageNumber = 1;
        int pageSize = 2;
        //Then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees")
                        .param("pageNumber", String.valueOf(pageNumber))
                        .param("pageSize", String.valueOf(pageSize)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(pageSize)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

}


