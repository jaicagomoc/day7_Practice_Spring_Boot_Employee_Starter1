package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class EmployeeRepository{
    private static final List<Employee> employees = new ArrayList<>();
    static {
        long companyId = 1L;
        employees.add(new Employee(1L, "Jess", 25, "male", companyId, 134324));
        employees.add(new Employee(2L, "Jessr", 25, "male", companyId, 134324));
        employees.add(new Employee(3L, "Alice", 25, "female", companyId, 134324));
        employees.add(new Employee(4L, "Leah", 25, "female", companyId, 134324));
        employees.add(new Employee(5L, "Jessriel", 25, "male", companyId, 134324));

    }
    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee saveEmployee(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployee(Employee updatedEmployee) {
        Employee existingEmployee = employees.stream()
                .filter(employee -> employee.getId() == updatedEmployee.getId())
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        return existingEmployee;
    }

    public void deleteEmployee(Long id) {
        employees.removeIf(employee -> employee.getId() == id);
    }

    public List<Employee> getEmployeesByPage(int pageNumber, int pageSize) {
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, employees.size());

        return IntStream.range(startIndex, endIndex)
                .mapToObj(employees::get)
                .collect(Collectors.toList());
    }
}