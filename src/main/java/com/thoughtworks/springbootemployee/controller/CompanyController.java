package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
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
    public Company saveCompany(@RequestBody Company company) {
        companyRepository.saveCompany(company);
        return companyRepository.saveCompany(company);
    }

    @PutMapping("/{companyId}")
    public Company updateCompany(@PathVariable Long companyId, @RequestBody Company updatedCompany) {
        Company existingCompany = companyRepository.findById(companyId);
        if (existingCompany == null) {
            throw new EmployeeNotFoundException();
        }
        existingCompany.setName(updatedCompany.getName());
        return companyRepository.updateCompany(updatedCompany);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable Long companyId) {
        return employeeRepository.getEmployeesByCompanyId(companyId);
    }

}
