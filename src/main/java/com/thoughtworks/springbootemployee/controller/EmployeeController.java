package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> listAll() {
        return employeeRepository.getEmployees();
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
    public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) {
        employeeRepository.saveEmployee(employee);
        return new ResponseEntity<>(employee.getName() + " was added to the list of Employee.", HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id);
        if (existingEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        return new ResponseEntity<>(existingEmployee.getName() + " was updated.", HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        Employee existingEmployee = employeeRepository.findById(id);
        if (existingEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        employeeRepository.deleteEmployee(id);
        return new ResponseEntity<>(existingEmployee.getName() + " was deleted.", HttpStatus.OK);
    }


}
