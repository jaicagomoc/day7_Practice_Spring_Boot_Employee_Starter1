package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class EmployeeRepository {


    private static final Long START_ID_MINUS_ONE = 0L;
    private static final long ID_INCREMENT = 1;
    private final CompanyRepository companyRepository;
    private static final List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee(1L, "Alice", 24, "male", 134324, 1L));
        employees.add(new Employee(2L, "Ra", 25, "male", 134324, 1L));
        employees.add(new Employee(3L, "Jai", 25, "female", 134324, 2L));
        employees.add(new Employee(4L, "Leah", 25, "female", 134324, 1L));
        employees.add(new Employee(5L, "Nina", 25, "male", 134324, 1L));
    }

    public EmployeeRepository(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee findById(Long id) {
        return employees.stream().filter(employee -> Objects.equals(employee.getId(), id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public Employee insertEmployeeBy(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployee(Employee updatedEmployee) {
        Employee existingEmployee = employees.stream()
                .filter(employee -> Objects.equals(employee.getId(), updatedEmployee.getId()))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setGender(updatedEmployee.getGender());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setCompanyId(updatedEmployee.getCompanyId());
        return existingEmployee;
    }


    public Employee deleteEmployeeById(Long id) {
        employees.removeIf(employee -> Objects.equals(employee.getId(), id));
        return null;
    }

    public List<Employee> getEmployeesByPage(int pageNumber, int pageSize) {
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, employees.size());

        return IntStream.range(startIndex, endIndex).mapToObj(employees::get).collect(Collectors.toList());
    }

    public List<Employee> getEmployeesByCompanyId(Long id) {
        Company company = companyRepository.findById(id);
        if (company == null) {
            return Collections.emptyList();
        }
        return employees.stream()
                .filter(employee -> employee.getCompanyId().equals(id))
                .collect(Collectors.toList());
    }


    public void cleanAllEmployees() {
        employees.clear();
    }

    public Employee saveEmployee(Employee employee) {
        Long id = generateNextId();
        employee.setId(id);
        employees.add(employee);

        return employee;
    }

    private Long generateNextId() {
        return employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(START_ID_MINUS_ONE) + ID_INCREMENT;
    }

    public List<Employee> listAll() {
        return employees;
    }
}