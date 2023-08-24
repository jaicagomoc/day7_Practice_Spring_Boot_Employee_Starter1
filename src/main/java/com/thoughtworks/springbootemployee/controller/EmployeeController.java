package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    //TODO: should split the repository declarations into service packages
    private final EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
    }

    //TODO: can remove this since it has no usage
    @GetMapping
    public List<Employee> listAllEmployees() {
        return employeeRepository.listAll();
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> listAll(@RequestParam(required = false) int pageNumber,
                                  @RequestParam(required = false) int pageSize) {
        return employeeRepository.getEmployeesByPage(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {

        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findByGender(@RequestParam String gender) {
        return employeeRepository.findByGender(gender);
    }


    @PostMapping
    public Employee insertEmployeeById(@RequestBody Employee employee) {
        return employeeRepository.insertEmployeeBy(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id);
        if (existingEmployee == null) {
            throw new EmployeeNotFoundException();
        }
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setGender(updatedEmployee.getGender());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setCompanyId(updatedEmployee.getCompanyId());

        return employeeRepository.updateEmployee(existingEmployee);
    }


    @DeleteMapping("/{id}")
    public Employee deleteEmployeeById(@PathVariable Long id) {
        Employee existingEmployee = employeeRepository.findById(id);
        if (existingEmployee == null) {
            throw new EmployeeNotFoundException();
        }
        employeeRepository.deleteEmployeeById(id);
        return employeeRepository.deleteEmployeeById(id);
    }


}