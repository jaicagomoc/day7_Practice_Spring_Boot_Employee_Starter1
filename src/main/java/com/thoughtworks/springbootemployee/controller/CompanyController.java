package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping
    public List<Company> listAll() {
        return companyRepository.getCompanies();
    }
    @GetMapping("/{id}")
    public Company findById(@PathVariable Long id) {
        return companyRepository.findById(id);
    }
    @PostMapping
    public ResponseEntity<String> saveCompany(@RequestBody Company company) {
        companyRepository.saveCompany(company);
        return new ResponseEntity<>(company.getName() + " was added to the list of Employee.", HttpStatus.CREATED);
    }
}
