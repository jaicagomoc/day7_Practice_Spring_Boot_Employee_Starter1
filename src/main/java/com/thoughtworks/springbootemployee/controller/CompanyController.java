package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CompanyController(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Company> listAll() {
        return companyRepository.getCompanies();
    }

    @GetMapping("/{companyId}")
    public Company findById(@PathVariable Long companyId) {
        return companyRepository.findById(companyId);
    }

    @PostMapping
    public ResponseEntity<String> saveCompany(@RequestBody Company company) {
        companyRepository.saveCompany(company);
        return new ResponseEntity<>(company.getName() + " was added to the list of Employee.", HttpStatus.CREATED);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<String> updateCompany(@PathVariable Long companyId, @RequestBody Company updatedCompany) {
        Company existingCompany = companyRepository.findById(companyId);
        ResponseEntity<String> build = getStringResponseEntity(existingCompany);
        if (build != null) return build;
        existingCompany.setName(updatedCompany.getName());
        return new ResponseEntity<>(existingCompany.getName() + " was updated.", HttpStatus.OK);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable Long companyId) {
        return employeeRepository.getEmployeesByCompanyId(companyId);
    }

    private static ResponseEntity<String> getStringResponseEntity(Company existingCompany) {
        if (existingCompany == null) {
            return ResponseEntity.notFound().build();
        }
        return null;
    }

}
