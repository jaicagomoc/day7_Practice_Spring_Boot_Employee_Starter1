package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setUp() {
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    void should_return_created_employee_when_create_given_employee_service_and_valid_age() {
        //given
        Employee employee = new Employee(1L, "Alice", 25, "Female", 9000, 1L);
        Employee savedEmployee = new Employee(1L, "Alice", 24, "Female", 9000, 1L);
        when(mockedEmployeeRepository.saveEmployee(employee)).thenReturn(savedEmployee);

        //when
        Employee employeeResponse = employeeService.create(employee);

        //then
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Alice", employeeResponse.getName());
        assertEquals(24, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(9000, employeeResponse.getSalary());
    }


    @Test
    void should_throw_exception_when_create_given_employee_whose_age_is_less_than_18() {
        //given
        Employee employee = new Employee(null, "Alice", 12, "Female", 9000, 1L);

        //when
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
        //then
        assertEquals("Employee must be 18-65 years old", employeeCreateException.getMessage());

    }

}
