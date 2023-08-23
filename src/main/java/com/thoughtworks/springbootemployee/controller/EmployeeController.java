package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    private final EmployeeRepository employeeRepository;

    private final CompanyRepository companyRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }
    @GetMapping
    public List<Employee> listAllEmployees(){
        return employeeRepository.listAll();
    }

    @GetMapping(params = {"pageNumber","pageSize"})
    public List<Employee> listAll(@RequestParam(defaultValue = "1") int pageNumber,
                                  @RequestParam(defaultValue = "5") int pageSize) {
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
    public ResponseEntity<String> insertEmployeeById(@RequestBody Employee employee) {
        employeeRepository.insertEmployeeBy(employee);
        return new ResponseEntity<>(employee.getName() + " was added to the list of Employee.", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployeeById(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id);
        ResponseEntity<String> build = getStringResponseEntity(existingEmployee);
        if (build != null) return build;
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        return new ResponseEntity<>(existingEmployee.getName() + " was updated.", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        Employee existingEmployee = employeeRepository.findById(id);
        ResponseEntity<String> build = getStringResponseEntity(existingEmployee);
        if (build != null) return build;
        employeeRepository.deleteEmployeeById(id);
        return new ResponseEntity<>(existingEmployee.getName() + " was deleted.", HttpStatus.OK);
    }

    private static ResponseEntity<String> getStringResponseEntity(Employee existingEmployee) {
        if (existingEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        return null;
    }


}