package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1L, "FirstCompany"));
        companies.add(new Company(2L, "SecondCompany"));
        companies.add(new Company(3L, "ThirdCompany"));
        companies.add(new Company(4L, "FourthCompany"));
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public Company findById(Long companyId) {
        return companies.stream().filter(company -> company.getId() == companyId).findFirst().orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> getEmployeesByPage(int pageNumber, int pageSize) {
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, companies.size());

        return IntStream.range(startIndex, endIndex).mapToObj(companies::get).collect(Collectors.toList());
    }

    public Company saveCompany(Company company) {
        companies.add(company);
        return company;
    }

    public Company updateCompany(Company updatedCompany) {
        Company existingCompany = companies.stream().filter(company -> company.getId() == updatedCompany.getId()).findFirst().orElseThrow(EmployeeNotFoundException::new);
        existingCompany.setName(updatedCompany.getName());
        return existingCompany;
    }

    public void cleanAllCompanies() {
        companies.clear();
    }
}